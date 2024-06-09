package pg.rsww.ChangeOfferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@Component
public class CommandsGenerator {
    private static final Logger log = LoggerFactory.getLogger(CommandsGenerator.class);
    private static final Random random = new Random();
    private final ChangeOfferService changeOfferService;

    @Autowired
    public CommandsGenerator(ChangeOfferService changeOfferService) {
        this.changeOfferService = changeOfferService;
    }


    @Scheduled(fixedRate = 500000)
    public void reportCurrentTime() {
        Command[] availableCommands = Command.values();
        Command chosenCommand = availableCommands[random.nextInt(availableCommands.length)];
        if(chosenCommand.equals(Command.REMOVE_HOTEL)) {
            changeOfferService.generateRemoveHotelCommand();
        }
        if(chosenCommand.equals(Command.REMOVE_FLIGHT)) {
            changeOfferService.generateRemoveFlightCommand();
        }
        if(chosenCommand.equals(Command.CHANGE_FLIGHT_PRICE)) {
            changeOfferService.generateChangeFlightPriceCommand();
        }
        if(chosenCommand.equals(Command.CHANGE_ROOM_PRICE)) {
            changeOfferService.generateChangeRoomPriceCommand();
        }
    }

    public enum Command {
        REMOVE_HOTEL, REMOVE_FLIGHT, CHANGE_ROOM_PRICE, CHANGE_FLIGHT_PRICE
    }
}
