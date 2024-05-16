package pg.rsww.OfferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsRequest;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsResponse;
import pg.rsww.OfferService.query.accommodation.GetHotelInfoRequest;
import pg.rsww.OfferService.query.accommodation.GetHotelInfoResponse;
import pg.rsww.OfferService.query.country.CountryRequest;
import pg.rsww.OfferService.query.country.CountryResponse;
import pg.rsww.OfferService.query.offer.*;
import pg.rsww.OfferService.query.transport.Flight;
import pg.rsww.OfferService.query.transport.GetFlightInfoRequest;
import pg.rsww.OfferService.query.transport.GetFlightsInfoResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
// TODO - Do składania oferty dodać dane pobierane z TransportService
// TODO - Ogarnąć może DTO jakieś

@Service
public class OfferService {
    private final AsyncRabbitTemplate rabbitTemplate;
    private final static String DEPARTURE_COUNTRY = "Polska";

    private final static Logger log = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    public OfferService(AsyncRabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public GetAllOffersResponse getAllOffers(GetAllOffersRequest getAllOffersRequest) {
        GetAllHotelsResponse getAllHotelsResponse = null;
        GetFlightsInfoResponse getAllFlightsToResponse = null;
        GetFlightsInfoResponse getAllFlightsFromResponse = null;

        // If dates are not chosen - it shows offer for a week from following day
        LocalDate startDate = getAllOffersRequest.getStartDate();
        LocalDate endDate = getAllOffersRequest.getEndDate();
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1);
        }
        if (endDate == null) {
            endDate = startDate.plusDays(7);
        }
        CompletableFuture<GetAllHotelsResponse> hotelsResponse = rabbitTemplate.convertSendAndReceiveAsType("hotel-all-request-queue", new GetAllHotelsRequest(UUID.randomUUID(), getAllOffersRequest.getCountry(), startDate, endDate, getAllOffersRequest.getNumberOfAdults(), getAllOffersRequest.getNumberOfChildrenUnder10(), getAllOffersRequest.getNumberOfChildrenUnder18()), new ParameterizedTypeReference<>(){});
        try {
            getAllHotelsResponse = hotelsResponse.get();
        } catch (Exception e) {
            log.warn("GetAllHotelsRequest got timeout");
        }
        log.info(String.format("Received getAllHotelsResponse %s", getAllHotelsResponse));

        CompletableFuture<GetFlightsInfoResponse> getFlightsToInfoResponse = rabbitTemplate.convertSendAndReceiveAsType("FindFlightQueue", new GetFlightInfoRequest(startDate.toString()), new ParameterizedTypeReference<>(){});
        try {
            getAllFlightsToResponse = getFlightsToInfoResponse.get();
        } catch (Exception e) {
            log.warn("GetFlightsToInfoRequest got timeout");
        }
        log.info(String.format("Received getAllFlightsToResponse %s", getAllFlightsToResponse));

        CompletableFuture<GetFlightsInfoResponse> getFlightsFromInfoResponse = rabbitTemplate.convertSendAndReceiveAsType("FindFlightQueue", new GetFlightInfoRequest(endDate.toString()), new ParameterizedTypeReference<>(){});
        try {
            getAllFlightsFromResponse = getFlightsFromInfoResponse.get();
        } catch (Exception e) {
            log.warn("GetFlightsFromInfoRequest got timeout");
        }
        log.info(String.format("Received getAllFlightsFromResponse %s", getAllFlightsFromResponse));


        GetAllOffersResponse  response = new GetAllOffersResponse();
        response.setOffers(new ArrayList<>());
        if(getAllHotelsResponse == null || getAllHotelsResponse.getHotelsList().isEmpty()) {
            response.setResponse(false);
            return response;
        }
        if(getAllFlightsToResponse == null || getAllFlightsToResponse.getFlights().isEmpty()) {
            response.setResponse(false);
            return response;
        }
        if(getAllFlightsFromResponse == null || getAllFlightsFromResponse.getFlights().isEmpty()) {
            response.setResponse(false);
            return response;
        }
        for (GetAllHotelsResponse.HotelShortModel hotel: getAllHotelsResponse.getHotelsList()) {
            boolean isFlightToAvailable = false;
            boolean isFlightFromAvailable = false;
            for (Flight flight: getAllFlightsToResponse.getFlights()) {
                if (flight.hasAvailableSits(getAllOffersRequest.getNumberOfAdults() + getAllOffersRequest.getNumberOfChildrenUnder10() + getAllOffersRequest.getNumberOfChildrenUnder18())) {
                    if (flight.getArrivalCountry().equals(hotel.getCountry()) && flight.getDepartureCountry().equals(DEPARTURE_COUNTRY)) {
                        isFlightToAvailable = true;
                    }
                }
            }
            for (Flight flight: getAllFlightsFromResponse.getFlights()) {
                if (flight.hasAvailableSits(getAllOffersRequest.getNumberOfAdults() + getAllOffersRequest.getNumberOfChildrenUnder10() + getAllOffersRequest.getNumberOfChildrenUnder18())) {
                    if (flight.getDepartureCountry().equals(hotel.getCountry()) && flight.getArrivalCountry().equals(DEPARTURE_COUNTRY)) {
                        isFlightFromAvailable = true;
                    }
                }
            }
            if (isFlightToAvailable && isFlightFromAvailable) {
                response.getOffers().add(OfferModel.builder()
                                .country(hotel.getCountry())
                                .startDate(getAllOffersRequest.getStartDate())
                                .endDate(getAllOffersRequest.getEndDate())
                                .numberOfAdults(getAllOffersRequest.getNumberOfAdults())
                                .numberOfChildrenUnder10(getAllOffersRequest.getNumberOfChildrenUnder10())
                                .numberOfChildrenUnder18(getAllOffersRequest.getNumberOfChildrenUnder18())
                                .hotelName(hotel.getName())
                                .hotelUuid(hotel.getUuid())
                        .build());
                if (!response.isResponse()) {
                    response.setResponse(true);
                }
            }
        }
        return response;
    }

    public GetOfferInfoResponse getOfferInfo(GetOfferInfoRequest getOfferInfoRequest) {
        GetHotelInfoResponse getHotelInfoResponse = null;
        GetFlightsInfoResponse getAllFlightsToResponse = null;
        GetFlightsInfoResponse getAllFlightsFromResponse = null;

        // If dates are not chosen - it shows offer for a week from following day
        LocalDate startDate = getOfferInfoRequest.getStartDate();
        LocalDate endDate = getOfferInfoRequest.getEndDate();
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1);
        }
        if (endDate == null) {
            endDate = startDate.plusDays(7);
        }
        CompletableFuture<GetHotelInfoResponse> hotelResponse = rabbitTemplate.convertSendAndReceiveAsType("hotel-info-request-queue", new GetHotelInfoRequest(UUID.randomUUID(), getOfferInfoRequest.getHotelUuid(), startDate, endDate, getOfferInfoRequest.getNumberOfAdults(), getOfferInfoRequest.getNumberOfChildrenUnder10(), getOfferInfoRequest.getNumberOfChildrenUnder18()), new ParameterizedTypeReference<>(){});
        try {
            getHotelInfoResponse = hotelResponse.get();
        } catch (Exception e) {
            log.warn("GetHotelInfoRequest got timeout");
        }
        log.info(String.format("Received getHotelInfoResponse %s", getHotelInfoResponse));

        CompletableFuture<GetFlightsInfoResponse> getFlightsToInfoResponse = rabbitTemplate.convertSendAndReceiveAsType("FindFlightQueue", new GetFlightInfoRequest(startDate.toString()), new ParameterizedTypeReference<>(){});
        try {
            getAllFlightsToResponse = getFlightsToInfoResponse.get();
        } catch (Exception e) {
            log.warn("GetFlightsToInfoRequest got timeout");
        }
        log.info(String.format("Received getAllFlightsToResponse %s", getAllFlightsToResponse));

        CompletableFuture<GetFlightsInfoResponse> getFlightsFromInfoResponse = rabbitTemplate.convertSendAndReceiveAsType("FindFlightQueue", new GetFlightInfoRequest(endDate.toString()), new ParameterizedTypeReference<>(){});
        try {
            getAllFlightsFromResponse = getFlightsFromInfoResponse.get();
        } catch (Exception e) {
            log.warn("GetFlightsFromInfoRequest got timeout");
        }
        log.info(String.format("Received getAllFlightsFromResponse %s", getAllFlightsFromResponse));

        GetOfferInfoResponse response = new GetOfferInfoResponse();
        if(getHotelInfoResponse == null || !getHotelInfoResponse.isResultFound()) {
            response.setResponse(false);
            return response;
        }
        if(getAllFlightsToResponse == null || getAllFlightsToResponse.getFlights().isEmpty()) {
            response.setResponse(false);
            return response;
        }
        if(getAllFlightsFromResponse == null || getAllFlightsFromResponse.getFlights().isEmpty()) {
            response.setResponse(false);
            return response;
        }
        List<Flight> availableFlightsTo = new ArrayList<>();
        List<Flight> availableFlightsFrom = new ArrayList<>();

        for (Flight flight: getAllFlightsToResponse.getFlights()) {
            if (flight.hasAvailableSits(getOfferInfoRequest.getNumberOfAdults() + getOfferInfoRequest.getNumberOfChildrenUnder10() + getOfferInfoRequest.getNumberOfChildrenUnder18())) {
                if (flight.getArrivalCountry().equals(getHotelInfoResponse.getCountry()) && flight.getDepartureCountry().equals(DEPARTURE_COUNTRY)) {
                    // calculating full price for the flight
                    flight.setPrice(flight.getPrice() * (getOfferInfoRequest.getNumberOfAdults() + getOfferInfoRequest.getNumberOfChildrenUnder10() + getOfferInfoRequest.getNumberOfChildrenUnder18()));
                    availableFlightsTo.add(flight);
                }
            }
        }
        for (Flight flight: getAllFlightsFromResponse.getFlights()) {
            if (flight.hasAvailableSits(getOfferInfoRequest.getNumberOfAdults() + getOfferInfoRequest.getNumberOfChildrenUnder10() + getOfferInfoRequest.getNumberOfChildrenUnder18())) {
                if (flight.getDepartureCountry().equals(getHotelInfoResponse.getCountry()) && flight.getArrivalCountry().equals(DEPARTURE_COUNTRY)) {
                    // calculating full price for the flight
                    flight.setPrice(flight.getPrice() * (getOfferInfoRequest.getNumberOfAdults() + getOfferInfoRequest.getNumberOfChildrenUnder10() + getOfferInfoRequest.getNumberOfChildrenUnder18()));
                    availableFlightsFrom.add(flight);
                }
            }
        }
        if (availableFlightsTo.isEmpty() || availableFlightsFrom.isEmpty()) {
            response.setResponse(false);
            return response;
        }
        // calculating full price for the room
        List<OfferInfoModel.RoomInOfferModel> rooms = getHotelInfoResponse.getRooms().stream().map(roomTypeModel -> new OfferInfoModel.RoomInOfferModel(roomTypeModel.getType(), roomTypeModel.getPrice())).toList();
        for (OfferInfoModel.RoomInOfferModel room: rooms) {
            room.setPrice(room.getPrice() * getOfferInfoRequest.getNumberOfAdults() + room.getPrice() * getOfferInfoRequest.getNumberOfChildrenUnder10() * 0.5f + room.getPrice() * getOfferInfoRequest.getNumberOfChildrenUnder18() * 0.7f);
        }
        response.setResponse(true);
        response.setOffer(OfferInfoModel.builder()
                        .hotelName(getHotelInfoResponse.getName())
                        .country(getHotelInfoResponse.getCountry())
                        .stars(getHotelInfoResponse.getStars())
                        .rooms(rooms)
                        .availableFlightsTo(availableFlightsTo)
                        .availableFlightsFrom(availableFlightsFrom)
                .build());
        return response;
    }
    public CountryResponse getCountries(CountryRequest countryRequest) {
        CountryResponse response = new CountryResponse(new ArrayList<>());
        CompletableFuture<CountryResponse> countryResponseCompletableFuture = rabbitTemplate.convertSendAndReceiveAsType("country-accommodation-queue", countryRequest, new ParameterizedTypeReference<>(){});
        try {
            response = countryResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("CountryRequest got timeout");
        }
        return response;
    }
}
