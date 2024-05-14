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
    private int capacity;
    private String type;
    private float basePrice;
    private String hotelUuid;
    //  FIXME - może tutaj dodać listę rezerwacji, albo zostawić rezerwacje osobno i tam lista pokoji
    public Room(String uuid, int capacity, String type, float basePrice, String hotelUuid) {
        this.uuid = uuid;
        this.capacity = capacity;
        this.type = type;
        this.basePrice = basePrice;
        this.hotelUuid = hotelUuid;
    }
}
