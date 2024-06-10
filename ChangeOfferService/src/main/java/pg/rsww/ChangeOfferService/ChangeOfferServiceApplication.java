package pg.rsww.ChangeOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Scanner;

@EnableScheduling
@SpringBootApplication
public class ChangeOfferServiceApplication implements CommandLineRunner {

	private final ChangeOfferService changeOfferService;

	@Autowired
    public ChangeOfferServiceApplication(ChangeOfferService changeOfferService) {
        this.changeOfferService = changeOfferService;
    }

    public static void main(String[] args) {
		SpringApplication.run(ChangeOfferServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Scanner scanner = new Scanner(System.in);
//		while (true) {
//			String command = scanner.nextLine();
//			boolean quit = false;
//			String hotelUuid, flightUuid;
//			String roomType;
//			String changedPrice;
//			switch (command) {
//				case "h": // Show all hotels
//					changeOfferService.showAllHotels();
//					break;
//				case "r": // Show all room types from the hotel
//					System.out.println("Enter hotel UUID:");
//					hotelUuid = scanner.nextLine();
//					changeOfferService.showAllRoomTypes(hotelUuid);
//					break;
//				case "f": // Show all flights
//					changeOfferService.showAllFlights();
//					break;
//				case "rh": // Remove hotel
//					System.out.println("Enter hotel UUID:");
//					hotelUuid = scanner.nextLine();
//					changeOfferService.removeHotel(hotelUuid);
//					break;
//				case "rf": // Remove flight
//					System.out.println("Enter flight UUID:");
//					flightUuid = scanner.nextLine();
//					changeOfferService.removeFlight(flightUuid);
//					break;
//				case "cr": // Change price of room
//					System.out.println("Enter hotel UUID:");
//					hotelUuid = scanner.nextLine();
//					System.out.println("Enter room type:");
//					roomType = scanner.nextLine();
//					System.out.println("Enter changed price:");
//					changedPrice = scanner.nextLine();
//					changeOfferService.changeRoomPrice(hotelUuid, roomType, Float.parseFloat(changedPrice));
//					break;
//				case "cf": // Change price of flight
//					System.out.println("Enter flight UUID:");
//					flightUuid = scanner.nextLine();
//					System.out.println("Enter changed price:");
//					changedPrice = scanner.nextLine();
//					changeOfferService.changeFlightPrice(flightUuid, Float.parseFloat(changedPrice));
//					break;
//				case "q": // Quit command line runner
//					quit = true;
//					break;
//			}
//			if (quit)
//				break;
//		}
	}
}
