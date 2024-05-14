package pg.rsww.OfferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.OfferService.query.country.CountryRequest;
import pg.rsww.OfferService.query.country.CountryResponse;
import pg.rsww.OfferService.query.offer.GetAllOffersRequest;
import pg.rsww.OfferService.query.offer.GetAllOffersResponse;
import pg.rsww.OfferService.query.offer.GetOfferInfoRequest;
import pg.rsww.OfferService.query.offer.GetOfferInfoResponse;

@Component
public class OfferQueryListener {
    private final OfferService offerService;
    private final static Logger log = LoggerFactory.getLogger(OfferQueryListener.class);

    @Autowired
    public OfferQueryListener(OfferService offerService) {
        this.offerService = offerService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.offerAllRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelAllResponseQueue']}")
    public GetAllOffersResponse GetAllOffersHandler(GetAllOffersRequest getAllOffersRequest) {
        log.info(String.format("Received GetAllOffersRequest %s", getAllOffersRequest));
        return offerService.getAllOffers(getAllOffersRequest);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.offerInfoRequestQueue}")
    //@SendTo("#{environment['spring.rabbitmq.queue.hotelInfoResponseQueue']}")
    public GetOfferInfoResponse GetOfferInfoHandler(GetOfferInfoRequest getOfferInfoRequest) {
        log.info(String.format("Received GetOfferInfoRequest %s", getOfferInfoRequest));
        return offerService.getOfferInfo(getOfferInfoRequest);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.countryQueue}")
    public CountryResponse GetCountriesHandler(CountryRequest countryRequest) {
        log.info(String.format("Received CountryRequest %s", countryRequest));
        return offerService.getCountries(countryRequest);
    }

}
