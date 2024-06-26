package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.*;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;
import pg.rsww.AccommodationService.command.entity.command.ChangeRoomPriceCommand;
import pg.rsww.AccommodationService.command.entity.command.GetLastRoomChangesRequest;
import pg.rsww.AccommodationService.command.hotel.HotelEventRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class RoomCommandService {
    private final RoomEventRepository roomEventRepository;
    private final HotelEventRepository hotelEventRepository;

    private final RoomPriceChangeEventRepository roomPriceChangeEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RoomCommandService(RoomEventRepository roomEventRepository, HotelEventRepository hotelEventRepository, RoomPriceChangeEventRepository roomPriceChangeEventRepository, RabbitTemplate rabbitTemplate) {
        this.roomEventRepository = roomEventRepository;
        this.hotelEventRepository = hotelEventRepository;
        this.roomPriceChangeEventRepository = roomPriceChangeEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    public RoomAddedEvent addNewRoom(AddNewRoomCommand addNewRoomCommand) {
        RoomAddedEvent roomAddedEvent = new RoomAddedEvent(UUID.randomUUID(),
                addNewRoomCommand.getUuid(),
                addNewRoomCommand.getCapacity(),
                addNewRoomCommand.getType(),
                addNewRoomCommand.getBasePrice(),
                addNewRoomCommand.getHotelUuid());
        roomEventRepository.save(roomAddedEvent);
        //System.out.println(roomEventRepository.findAll());
        //rabbitTemplate.convertAndSend("room-created-queue", roomAddedEvent);
        return roomAddedEvent;
    }

    public RoomPriceChangeEvent changeRoomPrice(ChangeRoomPriceCommand changeRoomPriceCommand) {
        List<RoomEvent> roomAddedEventList = roomEventRepository.findAllByHotelUuid(changeRoomPriceCommand.getHotelUuid());
        float oldPrice = 0.0f;
        for (RoomEvent roomEvent: roomAddedEventList) {
            if (roomEvent instanceof RoomAddedEvent) {
                RoomAddedEvent roomAddedEvent = (RoomAddedEvent) roomEvent;
                oldPrice = roomAddedEvent.getBasePrice();
                break;
            }
        }

        String hotelName="";
        List<HotelEvent> hotelEventList = hotelEventRepository.findAllByHotelUuid(changeRoomPriceCommand.getHotelUuid());
        for (HotelEvent event: hotelEventList) {
            if (event instanceof HotelAddedEvent) {
                hotelName = ((HotelAddedEvent) event).getName();
            }
        }

        RoomPriceChangeEvent event = new RoomPriceChangeEvent(UUID.randomUUID(),
                changeRoomPriceCommand.getHotelUuid(),
                changeRoomPriceCommand.getRoomType(),
                oldPrice,
                changeRoomPriceCommand.getChangedPrice(),
                hotelName);
        roomPriceChangeEventRepository.save(event);
        return event;
    }

    public List<RoomPriceChangeEvent> getLastChangeEvents(GetLastRoomChangesRequest request) {
        return roomPriceChangeEventRepository.findAll().stream()
                .sorted(Comparator.comparing(Event::getTimeStamp).reversed()) // maybe .reversed()
                .limit(10)
                .toList();
    }
}
