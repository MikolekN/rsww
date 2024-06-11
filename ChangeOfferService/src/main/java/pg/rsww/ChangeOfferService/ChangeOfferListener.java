package pg.rsww.ChangeOfferService;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.ChangeOfferService.command.ChangeFlightPriceCommand;
import pg.rsww.ChangeOfferService.command.ChangeRoomPriceCommand;
import pg.rsww.ChangeOfferService.command.RemoveFlightCommand;
import pg.rsww.ChangeOfferService.command.RemoveHotelCommand;

@Component
public class ChangeOfferListener {
    private final ChangeOfferService changeOfferService;

    @Autowired
    public ChangeOfferListener(ChangeOfferService changeOfferService) {
        this.changeOfferService = changeOfferService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.GenerateFlightPriceChangeQueue}")
    public void changeFlightPriceCommandHandler(ChangeFlightPriceCommand command) {
        changeOfferService.changeFlightPrice(command.getUuid().toString(), command.getChangedPrice());
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GenerateRoomPriceChangeQueue}")
    public void changeRoomPriceCommandHandler(ChangeRoomPriceCommand command) {
        changeOfferService.changeRoomPrice(command.getHotelUuid().toString(), command.getRoomType(), command.getChangedPrice());
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GenerateFlightRemoveQueue}")
    public void removeFlightCommandHandler(RemoveFlightCommand command) {
        changeOfferService.removeFlight(command.getUuid().toString());
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GenerateHotelRemoveQueue}")
    public void removeHotelCommandHandler(RemoveHotelCommand command) {
        changeOfferService.removeHotel(command.getUuid().toString());
    }
}
