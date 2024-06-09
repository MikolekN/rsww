package pg.rsww.ChangeOfferService.entity;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private String id;
    private String uuid;
    private int capacity;
    private String type;
    private float basePrice;
    private String hotelUuid;
    public Room(String uuid, int capacity, String type, float basePrice, String hotelUuid) {
        this.uuid = uuid;
        this.capacity = capacity;
        this.type = type;
        this.basePrice = basePrice;
        this.hotelUuid = hotelUuid;
    }
}
