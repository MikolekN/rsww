package pg.rsww.AccommodationService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.entity.response.MakeNewReservationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class AccommodationServiceApplication implements CommandLineRunner {
	// TODO - Delete when debugging is not needed anymore
	private final RabbitTemplate rabbitTemplate;

	@Autowired
    public AccommodationServiceApplication(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public static void main(String[] args) {
		SpringApplication.run(AccommodationServiceApplication.class, args);
	}

	// TODO - Delete when debugging is not needed anymore
	@Override
	public void run(String... args) throws Exception {
		UUID testAddedHotelUuid = UUID.randomUUID();

		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		Scanner myObj = new Scanner(System.in);
		String x = myObj.nextLine();
		rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "HOTELTEST", "Francja"));
		x = myObj.nextLine();
		rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, 2, "type1", 240.5F, testAddedHotelUuid));
		rabbitTemplate.convertAndSend("room-create-queue", new AddNewRoomCommand(UUID.randomUUID(), 1, 2, "type2", 280.5F, testAddedHotelUuid));

		x = myObj.nextLine();
		UUID reservationToCancelUuid = UUID.randomUUID();
		MakeNewReservationResponse r = (MakeNewReservationResponse) rabbitTemplate.convertSendAndReceive("reservation-make-queue", new MakeNewReservationCommand(reservationToCancelUuid, LocalDateTime.now(), LocalDate.now(), LocalDate.now(), "type1", 1, 2, testAddedHotelUuid));
		System.out.println(r);
		x = myObj.nextLine();
		// Cancelling reservation
		rabbitTemplate.convertSendAndReceive("reservation-cancel-queue", new CancelReservationCommand(UUID.randomUUID(), LocalDateTime.now(), reservationToCancelUuid));
		x = myObj.nextLine();
		//
		rabbitTemplate.convertSendAndReceive("reservation-make-queue", new MakeNewReservationCommand(UUID.randomUUID(), LocalDateTime.now(), LocalDate.now(), LocalDate.now(), "type1", 1, 2, testAddedHotelUuid));
		x = myObj.nextLine();
		rabbitTemplate.convertSendAndReceive("reservation-make-queue", new MakeNewReservationCommand(UUID.randomUUID(), LocalDateTime.now(), LocalDate.of(2000,1,1), LocalDate.of(2000,1,10), "type1", 1, 2, testAddedHotelUuid));
		x = myObj.nextLine();
		MakeNewReservationResponse r2 = (MakeNewReservationResponse) rabbitTemplate.convertSendAndReceive("reservation-make-queue", new MakeNewReservationCommand(UUID.randomUUID(), LocalDateTime.now(), LocalDate.of(2000,1,1), LocalDate.of(2000,1,10), "type1", 1, 2, testAddedHotelUuid));
		System.out.println(r2);
		x = myObj.nextLine();
		MakeNewReservationResponse r3 = (MakeNewReservationResponse) rabbitTemplate.convertSendAndReceive("reservation-make-queue", new MakeNewReservationCommand(UUID.randomUUID(), LocalDateTime.now(), LocalDate.of(2000,1,1), LocalDate.of(2000,1,10), "type1", 1, 2, testAddedHotelUuid));
		System.out.println(r3);

	}
}
