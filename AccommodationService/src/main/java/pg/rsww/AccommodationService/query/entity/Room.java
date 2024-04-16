package pg.rsww.AccommodationService.query.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("rooms")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    private String id;
    private String uuid;
    private int numberOfAdults;
    private int numberOfChildren;
    private String type;
    private float price;
    private String hotelUuid;
    //  FIXME - może tutaj dodać listę rezerwacji, albo zostawić rezerwacje osobno i tam lista pokoji
    public Room(String uuid, int numberOfAdults, int numberOfChildren, String type, float price, String hotelUuid) {
        this.uuid = uuid;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.type = type;
        this.price = price;
        this.hotelUuid = hotelUuid;
    }
}
