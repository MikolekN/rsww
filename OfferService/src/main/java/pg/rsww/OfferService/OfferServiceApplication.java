package pg.rsww.OfferService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pg.rsww.OfferService.query.offer.GetAllOffersRequest;
import pg.rsww.OfferService.query.offer.GetOfferInfoRequest;

import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class OfferServiceApplication implements CommandLineRunner {
	// TODO - Delete when debugging is not needed anymore
	private final RabbitTemplate rabbitTemplate;
	// TODO - zdecydować, czy zapisywać oferty do bazy danych, czy może zostać tworzenie ich w locie
	@Autowired
    public OfferServiceApplication(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public static void main(String[] args) {
		SpringApplication.run(OfferServiceApplication.class, args);
	}

	// TODO - Delete when debugging is not needed anymore
	@Override
	public void run(String... args) throws Exception {
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		System.out.println("pppp");
		Scanner myObj = new Scanner(System.in);
		String x = myObj.nextLine();
		Object r = rabbitTemplate.convertSendAndReceive("offer-all-request-queue", new GetAllOffersRequest(UUID.randomUUID(), "", null, null, 1, 2));
		x = myObj.nextLine();
		Object r2 = rabbitTemplate.convertSendAndReceive("offer-info-request-queue", new GetOfferInfoRequest(UUID.randomUUID(), UUID.fromString(x), "", null, null, 1, 2));
		//rabbitTemplate.convertAndSend("hotel-create-queue", new AddNewHotelCommand(testAddedHotelUuid, "HOTELTEST", "Francja"));

	}
}
