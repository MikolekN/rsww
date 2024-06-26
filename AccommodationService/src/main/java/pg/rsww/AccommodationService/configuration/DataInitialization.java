package pg.rsww.AccommodationService.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.Event;
import pg.rsww.AccommodationService.command.entity.HotelEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.hotel.HotelEventRepository;
import pg.rsww.AccommodationService.query.hotel.HotelRepository;
import pg.rsww.AccommodationService.query.entity.Hotel;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class DataInitialization {
    private final HotelRepository hotelRepository;
    private final HotelEventRepository hotelEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DataInitialization(HotelRepository hotelRepository, RabbitTemplate rabbitTemplate,
                              HotelEventRepository hotelEventRepository) {
        this.hotelRepository = hotelRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.hotelEventRepository = hotelEventRepository;
    }

    @PostConstruct
    private void init() {
        //System.out.println("DATA INITIALIZATION");
        //hotelRepository.save(new Hotel(UUID.randomUUID(), "Sheraton", "USA"));
        //hotelRepository.save(new Hotel(UUID.randomUUID(), "HOTEL123", "Poland"));
        /*UUID testAddedHotelUuid = UUID.randomUUID();
        rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "Sheraton", "Hiszpania", 5));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type2", 280.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 2, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, "type2", 280.5F, testAddedHotelUuid));

        testAddedHotelUuid = UUID.randomUUID();
        rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "Hilton", "Niemcy", 3));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type2", 280.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 2, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, "type2", 280.5F, testAddedHotelUuid));

        testAddedHotelUuid = UUID.randomUUID();
        rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "Ibis", "Grecja", 2));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 3, "type2", 280.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 2, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, "type2", 280.5F, testAddedHotelUuid));
*/
        //System.out.println(hotelRepository.findAll());
        //rabbitTemplate.convertAndSend("accommodation-topic", "", "Hello, !");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //rabbitTemplate.convertAndSend("accommodation-queue-2", new HotelAddedEvent(UUID.randomUUID(),"fr", "Polska"));
        /*rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "HOTELTEST", "Francja"));
        rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, 2, "type1", 240.5F, testAddedHotelUuid));
        rabbitTemplate.convertAndSend("reservation-make-queue", new MakeNewReservationCommand(UUID.randomUUID(), LocalDateTime.now(), LocalDate.now(), LocalDate.now(), "type1", 1, 2, testAddedHotelUuid));
        //rabbitTemplate.convertAndSend("reservation-cancel-queue", new CancelReservationCommand(UUID.fromString("88bd961e-a6de-4f46-879b-7ba8b1128509")));*/
        //hotelEventRepository.save(new Event(UUID.randomUUID(), LocalDateTime.now()));
        //hotelEventRepository.save(new HotelEvent(UUID.randomUUID(), UUID.randomUUID()));
        //System.out.println(hotelEventRepository.findAll());

    }

}
