package pg.rsww.ChangeOfferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import pg.rsww.ChangeOfferService.command.ChangeFlightPriceCommand;
import pg.rsww.ChangeOfferService.command.ChangeRoomPriceCommand;
import pg.rsww.ChangeOfferService.command.RemoveFlightCommand;
import pg.rsww.ChangeOfferService.command.RemoveHotelCommand;
import pg.rsww.ChangeOfferService.entity.Flight;
import pg.rsww.ChangeOfferService.entity.Hotel;
import pg.rsww.ChangeOfferService.entity.RoomType;
import pg.rsww.ChangeOfferService.query.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ChangeOfferService {
    private final AsyncRabbitTemplate asyncRabbitTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final static Logger log = LoggerFactory.getLogger(ChangeOfferService.class);
    private static final Random random = new Random();

    private final Queue getAllHotelsQueue;
    private final Queue getAllRoomTypesQueue;
    private final Queue getAllFlightsQueue;
    private final Queue removeHotelQueue;
    private final Queue removeFlightQueue;
    private final Queue changeRoomPriceQueue;
    private final Queue changeFlightPriceQueue;


    @Autowired
    public ChangeOfferService(AsyncRabbitTemplate asyncRabbitTemplate, RabbitTemplate rabbitTemplate, Queue getAllHotelsQueue, Queue getAllRoomTypesQueue, Queue getAllFlightsQueue, Queue removeHotelQueue, Queue removeFlightQueue, Queue changeRoomPriceQueue, Queue changeFlightPriceQueue) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.getAllHotelsQueue = getAllHotelsQueue;
        this.getAllRoomTypesQueue = getAllRoomTypesQueue;
        this.getAllFlightsQueue = getAllFlightsQueue;
        this.removeHotelQueue = removeHotelQueue;
        this.removeFlightQueue = removeFlightQueue;
        this.changeRoomPriceQueue = changeRoomPriceQueue;
        this.changeFlightPriceQueue = changeFlightPriceQueue;
    }

    public void generateChangeFlightPriceCommand() {
        ChangeFlightPriceCommand command;

        GetFlightsInfoResponse getFlightsInfoResponse;
        CompletableFuture<GetFlightsInfoResponse> getFlightsInfoResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllFlightsQueue.getName(), new GetFlightsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getFlightsInfoResponse = getFlightsInfoResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetFlightsRequest got timeout");
            return;
        }
        List<Flight> flightList = getFlightsInfoResponse.getFlights();
        Flight flightToChangePrice = flightList.get(random.nextInt(flightList.size()));

        float changedPrice = flightToChangePrice.getPrice();
        int priceChange = random.nextInt(50) + 1;
        boolean priceChangeIncrease = random.nextBoolean();
        if (priceChangeIncrease) {
            changedPrice += priceChange;
        }
        else {
            if (changedPrice - priceChange <= 0) {
                changedPrice += priceChange;
            }
            else {
                changedPrice -= priceChange;
            }
        }
        command = new ChangeFlightPriceCommand(flightToChangePrice.getId(), changedPrice);
        log.info(String.format("Generated ChangeFlightPriceCommand %s", command));
        rabbitTemplate.convertAndSend(changeFlightPriceQueue.getName(), command);
    }

    public void generateChangeRoomPriceCommand() {
        ChangeRoomPriceCommand command;

        GetHotelsResponse getHotelsResponse;
        CompletableFuture<GetHotelsResponse> getHotelsResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllHotelsQueue.getName(), new GetHotelsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getHotelsResponse = getHotelsResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetHotelsRequest got timeout");
            return;
        }
        List<Hotel> hotelList = getHotelsResponse.getHotels();
        Hotel hotelToChangePricesIn = hotelList.get(random.nextInt(hotelList.size()));

        GetRoomsResponse getRoomsResponse;
        CompletableFuture<GetRoomsResponse> getRoomsResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllRoomTypesQueue.getName(), new GetRoomsRequest(UUID.randomUUID(), hotelToChangePricesIn.getUuid()), new ParameterizedTypeReference<>(){});
        try {
            getRoomsResponse = getRoomsResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetRoomsRequest got timeout");
            return;
        }
        List<RoomType> roomTypeList = getRoomsResponse.getRoomTypes();
        RoomType roomTypeToChangePrice = roomTypeList.get(random.nextInt(roomTypeList.size()));
        float changedPrice = roomTypeToChangePrice.getBasePrice();
        int priceChange = random.nextInt(50) + 1;
        boolean priceChangeIncrease = random.nextBoolean();
        if (priceChangeIncrease) {
            changedPrice += priceChange;
        }
        else {
            if (changedPrice - priceChange <= 0) {
                changedPrice += priceChange;
            }
            else {
                changedPrice -= priceChange;
            }
        }
        command = new ChangeRoomPriceCommand(UUID.fromString(hotelToChangePricesIn.getUuid()), roomTypeToChangePrice.getType(), changedPrice);
        log.info(String.format("Generated ChangeRoomPriceCommand %s", command));
        rabbitTemplate.convertAndSend(changeRoomPriceQueue.getName(), command);
    }

    public void generateRemoveFlightCommand() {
        RemoveFlightCommand command;

        GetFlightsInfoResponse getFlightsInfoResponse;
        CompletableFuture<GetFlightsInfoResponse> getFlightsInfoResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllFlightsQueue.getName(), new GetFlightsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getFlightsInfoResponse = getFlightsInfoResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetFlightsRequest got timeout");
            return;
        }
        List<Flight> flightList = getFlightsInfoResponse.getFlights();
        Flight flightToRemove = flightList.get(random.nextInt(flightList.size()));
        command = new RemoveFlightCommand(flightToRemove.getId());
        log.info(String.format("Generated RemoveFlightCommand %s", command));
        rabbitTemplate.convertAndSend(removeFlightQueue.getName(), command);
    }

    public void generateRemoveHotelCommand() {
        RemoveHotelCommand command;

        GetHotelsResponse getHotelsResponse;
        CompletableFuture<GetHotelsResponse> getHotelsResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllHotelsQueue.getName(), new GetHotelsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getHotelsResponse = getHotelsResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetHotelsRequest got timeout");
            return;
        }
        List<Hotel> hotelList = getHotelsResponse.getHotels();
        Hotel hotelToRemove = hotelList.get(random.nextInt(hotelList.size()));
        command = new RemoveHotelCommand(UUID.fromString(hotelToRemove.getUuid()));
        log.info(String.format("Generated RemoveHotelCommand %s", command));
        rabbitTemplate.convertAndSend(removeHotelQueue.getName(), command);
    }

    // Command line runner functions

    public void showAllHotels() {
        GetHotelsResponse getHotelsResponse;
        CompletableFuture<GetHotelsResponse> getHotelsResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllHotelsQueue.getName(), new GetHotelsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getHotelsResponse = getHotelsResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetHotelsRequest got timeout");
            return;
        }
        List<Hotel> hotelList = getHotelsResponse.getHotels();
        log.info("Hotels: " + hotelList);
    }

    public void showAllRoomTypes(String hotelUuid) {
        GetRoomsResponse getRoomsResponse;
        CompletableFuture<GetRoomsResponse> getRoomsResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllRoomTypesQueue.getName(), new GetRoomsRequest(UUID.randomUUID(), hotelUuid), new ParameterizedTypeReference<>(){});
        try {
            getRoomsResponse = getRoomsResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetRoomsRequest got timeout");
            return;
        }
        List<RoomType> roomTypeList = getRoomsResponse.getRoomTypes();
        log.info("Rooms: " + roomTypeList);

    }

    public void showAllFlights() {
        GetFlightsInfoResponse getFlightsInfoResponse;
        CompletableFuture<GetFlightsInfoResponse> getFlightsInfoResponseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(getAllFlightsQueue.getName(), new GetFlightsRequest(UUID.randomUUID()), new ParameterizedTypeReference<>(){});
        try {
            getFlightsInfoResponse = getFlightsInfoResponseCompletableFuture.get();
        } catch (Exception e) {
            log.warn("GetFlightsRequest got timeout");
            return;
        }
        List<Flight> flightList = getFlightsInfoResponse.getFlights();
        log.info("Flights: " + flightList);
    }

    public void removeHotel(String hotelUuid) {
        RemoveHotelCommand command = new RemoveHotelCommand(UUID.fromString(hotelUuid));
        log.info(String.format("Sending RemoveHotelCommand %s", command));
        rabbitTemplate.convertAndSend(removeHotelQueue.getName(), command);
    }
    public void removeFlight(String flightUuid) {
        RemoveFlightCommand command;
        command = new RemoveFlightCommand(UUID.fromString(flightUuid));
        log.info(String.format("Sending RemoveFlightCommand %s", command));
        rabbitTemplate.convertAndSend(removeFlightQueue.getName(), command);
    }

    public void changeRoomPrice(String hotelUuid, String roomType, float changedPrice) {
        ChangeRoomPriceCommand command = new ChangeRoomPriceCommand(UUID.fromString(hotelUuid), roomType, changedPrice);
        log.info(String.format("Sending ChangeRoomPriceCommand %s", command));
        rabbitTemplate.convertAndSend(changeRoomPriceQueue.getName(), command);

    }
    public void changeFlightPrice(String flightUuid, float changedPrice) {
        ChangeFlightPriceCommand command = new ChangeFlightPriceCommand(UUID.fromString(flightUuid), changedPrice);
        log.info(String.format("Sending ChangeFlightPriceCommand %s", command));
        rabbitTemplate.convertAndSend(changeFlightPriceQueue.getName(), command);
    }
}
