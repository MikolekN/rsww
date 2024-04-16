package pg.rsww.AccommodationService.query.hotel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.query.event.GetAllHotelsRequest;
import pg.rsww.AccommodationService.query.event.GetHotelInfoRequest;

@Component
public class HotelQueryListener {
    private final HotelService hotelService;

    @Autowired
    public HotelQueryListener(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // TODO getAvailableHotels
    @RabbitListener(queues = "hotel-all-request-queue")
    public void GetAllHotelsHandler(GetAllHotelsRequest getAllHotelsRequest) {
        System.out.println(getAllHotelsRequest);
        hotelService.getAllHotels(getAllHotelsRequest);
    }
    // TODO getHotelInfo
    @RabbitListener(queues = "hotel-info-request-queue")
    public void GetHotelInfoHandler(GetHotelInfoRequest getHotelInfoRequest) {
        System.out.println(getHotelInfoRequest);
        hotelService.getHotelInfo(getHotelInfoRequest);
    }

}