package pg.rsww.AccommodationService.query.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.query.changequery.GetHotelsRequest;
import pg.rsww.AccommodationService.query.changequery.GetHotelsResponse;
import pg.rsww.AccommodationService.query.event.*;

@Component
public class HotelQueryListener {
    private final HotelService hotelService;
    private final static Logger log = LoggerFactory.getLogger(HotelQueryListener.class);

    @Autowired
    public HotelQueryListener(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.hotelAllRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelAllResponseQueue']}")
    public GetAllHotelsResponse GetAllHotelsHandler(GetAllHotelsRequest getAllHotelsRequest) {
        log.info(String.format("Received GetAllHotelsRequest %s", getAllHotelsRequest));
        return hotelService.getAllHotels(getAllHotelsRequest);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.hotelInfoRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelInfoResponseQueue']}")
    public GetHotelInfoResponse GetHotelInfoHandler(GetHotelInfoRequest getHotelInfoRequest) {
        log.info(String.format("Received GetHotelInfoRequest %s", getHotelInfoRequest));
        return hotelService.getHotelInfo(getHotelInfoRequest);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.countryAccommodationQueue}")
    public CountryResponse getCountriesHandler(CountryRequest countryRequest) {
        log.info(String.format("Received CountryRequest %s", countryRequest));
        return hotelService.getCountries(countryRequest);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetAllHotelsQueue}")
    public GetHotelsResponse GetAllHotelsHandler(GetHotelsRequest getHotelsRequest) {
        return hotelService.getHotels(getHotelsRequest);
    }
}