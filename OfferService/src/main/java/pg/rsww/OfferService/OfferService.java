package pg.rsww.OfferService;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsRequest;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsResponse;
import pg.rsww.OfferService.query.accommodation.GetHotelInfoRequest;
import pg.rsww.OfferService.query.accommodation.GetHotelInfoResponse;
import pg.rsww.OfferService.query.offer.GetAllOffersRequest;
import pg.rsww.OfferService.query.offer.GetAllOffersResponse;
import pg.rsww.OfferService.query.offer.GetOfferInfoRequest;
import pg.rsww.OfferService.query.offer.GetOfferInfoResponse;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
// TODO - Do składania oferty dodać dane pobierane z TransportService
// TODO - Ogarnąć może DTO jakieś

@Service
public class OfferService {
    private final AsyncRabbitTemplate rabbitTemplate;

    @Autowired
    public OfferService(AsyncRabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public GetAllOffersResponse getAllOffers(GetAllOffersRequest getAllOffersRequest) {
        GetAllHotelsResponse getAllHotelsResponse = null;

        // If dates are not chosen - it shows offer for a week from following day
        LocalDate startDate = getAllOffersRequest.getStartDate();
        LocalDate endDate = getAllOffersRequest.getEndDate();
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1);
        }
        if (endDate == null) {
            endDate = startDate.plusDays(7);
        }
        CompletableFuture<GetAllHotelsResponse> hotelsResponse = rabbitTemplate.convertSendAndReceiveAsType("hotel-all-request-queue", new GetAllHotelsRequest(UUID.randomUUID(), getAllOffersRequest.getCountry(), startDate, endDate, getAllOffersRequest.getNumberOfAdults(), getAllOffersRequest.getNumberOfChildren()), new ParameterizedTypeReference<>(){});
        try {
            getAllHotelsResponse = hotelsResponse.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(getAllHotelsResponse);
        // TODO put getAllHotelsResponse and getAllTransportResponse together into offers and return
        return new GetAllOffersResponse(UUID.randomUUID());
    }

    public GetOfferInfoResponse getOfferInfo(GetOfferInfoRequest getOfferInfoRequest) {
        GetHotelInfoResponse getHotelInfoResponse = null;

        // If dates are not chosen - it shows offer for a week from following day
        LocalDate startDate = getOfferInfoRequest.getStartDate();
        LocalDate endDate = getOfferInfoRequest.getEndDate();
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1);
        }
        if (endDate == null) {
            endDate = startDate.plusDays(7);
        }
        CompletableFuture<GetHotelInfoResponse> hotelResponse = rabbitTemplate.convertSendAndReceiveAsType("hotel-info-request-queue", new GetHotelInfoRequest(UUID.randomUUID(), getOfferInfoRequest.getHotelUuid(), startDate, endDate, getOfferInfoRequest.getNumberOfAdults(), getOfferInfoRequest.getNumberOfChildren()), new ParameterizedTypeReference<>(){});
        try {
            getHotelInfoResponse = hotelResponse.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(getHotelInfoResponse);

        // TODO put getHotelInfoResponse and get?TransportResponse together into offers and return
        return new GetOfferInfoResponse(UUID.randomUUID());
    }
}
