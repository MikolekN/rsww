package pg.rsww.OfferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.OfferService.query.country.CountryRequest;
import pg.rsww.OfferService.query.country.CountryResponse;
import pg.rsww.OfferService.query.offer.GetAllOffersRequest;
import pg.rsww.OfferService.query.offer.GetAllOffersResponse;
import pg.rsww.OfferService.query.offer.GetOfferInfoRequest;
import pg.rsww.OfferService.query.offer.GetOfferInfoResponse;
import pg.rsww.OfferService.query.offerchange.GetOfferChangesRequest;
import pg.rsww.OfferService.query.offerchange.GetOfferChangesResponse;
import pg.rsww.OfferService.query.offerchange.OfferChangeEvent;
import pg.rsww.OfferService.query.offerchange.flight.FlightPriceChangedEvent;
import pg.rsww.OfferService.query.offerchange.flight.FlightRemovedEvent;
import pg.rsww.OfferService.query.offerchange.hotel.HotelRemovedEvent;
import pg.rsww.OfferService.query.offerchange.room.RoomPriceChangeEvent;

@Component
public class OfferQueryListener {
    private final OfferService offerService;
    private final static Logger log = LoggerFactory.getLogger(OfferQueryListener.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue flightRemovedEventFrontQueue;
    private final Queue flightPriceChangedEventFrontQueue;
    private final Queue hotelRemovedEventFrontQueue;
    private final Queue roomPriceChangedEventFrontQueue;
    private final Queue offerChangeFrontQueue;

    @Autowired
    public OfferQueryListener(OfferService offerService, RabbitTemplate rabbitTemplate, Queue flightRemovedEventFrontQueue, Queue flightPriceChangedEventFrontQueue, Queue hotelRemovedEventFrontQueue, Queue roomPriceChangedEventFrontQueue, Queue offerChangeFrontQueue) {
        this.offerService = offerService;
        this.rabbitTemplate = rabbitTemplate;
        this.flightRemovedEventFrontQueue = flightRemovedEventFrontQueue;
        this.flightPriceChangedEventFrontQueue = flightPriceChangedEventFrontQueue;
        this.hotelRemovedEventFrontQueue = hotelRemovedEventFrontQueue;
        this.roomPriceChangedEventFrontQueue = roomPriceChangedEventFrontQueue;
        this.offerChangeFrontQueue = offerChangeFrontQueue;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.offerAllRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelAllResponseQueue']}")
    public GetAllOffersResponse GetAllOffersHandler(GetAllOffersRequest getAllOffersRequest) {
        log.info(String.format("Received GetAllOffersRequest %s", getAllOffersRequest));
        GetAllOffersResponse response = offerService.getAllOffers(getAllOffersRequest);
        log.info(String.format("Responding with GetAllOffersResponse %s", response));
        return response;
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.offerInfoRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelInfoResponseQueue']}")
    public GetOfferInfoResponse GetOfferInfoHandler(GetOfferInfoRequest getOfferInfoRequest) {
        log.info(String.format("Received GetOfferInfoRequest %s", getOfferInfoRequest));
        GetOfferInfoResponse response = offerService.getOfferInfo(getOfferInfoRequest);
        log.info(String.format("Responding with GetOfferInfoResponse %s", response));
        return response;
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.countryQueue}")
    public CountryResponse GetCountriesHandler(CountryRequest countryRequest) {
        log.info(String.format("Received CountryRequest %s", countryRequest));
        CountryResponse response = offerService.getCountries(countryRequest);
        log.info(String.format("Responding with CountryResponse %s", response));
        return response;
    }
    // TODO - get last 10
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetLastOfferChangesQueue}")
    public GetOfferChangesResponse getOfferChangesHandler(GetOfferChangesRequest getOfferChangesRequest) {
        log.info(String.format("Received GetOfferChangesRequest %s", getOfferChangesRequest));
        GetOfferChangesResponse response = offerService.getLastOfferChanges(getOfferChangesRequest);
        log.info(String.format("Responding with GetOfferChangesResponse %s", response));
        return response;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.RoomPriceChangedEventOfferQueue}")
    public void roomPriceChangeEventHandler(RoomPriceChangeEvent roomPriceChangeEvent) {
        log.info(String.format("Received RoomPriceChangeEvent %s", roomPriceChangeEvent));
        OfferChangeEvent offerChangeEvent = new OfferChangeEvent(roomPriceChangeEvent.getUuid(),
                roomPriceChangeEvent.getTimeStamp(),
                String.format("%s from hotel %s price was changed from %s to %s", roomPriceChangeEvent.getRoomType(), roomPriceChangeEvent.getHotelName(), roomPriceChangeEvent.getOldPrice(), roomPriceChangeEvent.getNewPrice()));
        rabbitTemplate.convertAndSend(roomPriceChangedEventFrontQueue.getName(), roomPriceChangeEvent);
        rabbitTemplate.convertAndSend(offerChangeFrontQueue.getName(), offerChangeEvent);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.FlightPriceChangedEventQueue}")
    public void flightPriceChangeEventHandler(FlightPriceChangedEvent flightPriceChangedEvent) {
        log.info(String.format("Received FlightPriceChangedEvent %s", flightPriceChangedEvent));
        OfferChangeEvent offerChangeEvent = new OfferChangeEvent(flightPriceChangedEvent.getUuid(),
                flightPriceChangedEvent.getTimeStamp(),
                String.format("%s flight (%s) from %s to %s price was changed from %s to %s", flightPriceChangedEvent.getFlightUuid(), flightPriceChangedEvent.getDepartureDate(), flightPriceChangedEvent.getDepartureCountry(), flightPriceChangedEvent.getArrivalCountry(), flightPriceChangedEvent.getOldPrice(), flightPriceChangedEvent.getNewPrice()));
        rabbitTemplate.convertAndSend(flightPriceChangedEventFrontQueue.getName(), flightPriceChangedEvent);
        rabbitTemplate.convertAndSend(offerChangeFrontQueue.getName(), offerChangeEvent);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.FlightRemovedEventQueue}")
    public void flightRemovedEventHandler(FlightRemovedEvent flightRemovedEvent) {
        log.info(String.format("Received FlightRemovedEvent %s", flightRemovedEvent));
        OfferChangeEvent offerChangeEvent = new OfferChangeEvent(flightRemovedEvent.getUuid(),
                flightRemovedEvent.getTimeStamp(),
                String.format("%s flight (%s) from %s to %s was removed", flightRemovedEvent.getFlightUuid(), flightRemovedEvent.getDepartureDate(), flightRemovedEvent.getDepartureCountry(), flightRemovedEvent.getArrivalCountry()));
        rabbitTemplate.convertAndSend(flightRemovedEventFrontQueue.getName(), flightRemovedEvent);
        rabbitTemplate.convertAndSend(offerChangeFrontQueue.getName(), offerChangeEvent);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.HotelRemovedEventOfferQueue}")
    public void hotelRemovedEventHandler(HotelRemovedEvent hotelRemovedEvent) {
        log.info(String.format("Received HotelRemovedEvent %s", hotelRemovedEvent));
        OfferChangeEvent offerChangeEvent = new OfferChangeEvent(hotelRemovedEvent.getUuid(),
                hotelRemovedEvent.getTimeStamp(),
                String.format("%s hotel was removed", hotelRemovedEvent.getName()   ));
        rabbitTemplate.convertAndSend(hotelRemovedEventFrontQueue.getName(), hotelRemovedEvent);
        rabbitTemplate.convertAndSend(offerChangeFrontQueue.getName(), offerChangeEvent);
    }
}
