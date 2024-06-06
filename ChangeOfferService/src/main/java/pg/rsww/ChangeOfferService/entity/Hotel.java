package pg.rsww.ChangeOfferService.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    private String id;
    private String uuid;
    private String name;
    private String country;
    private int stars;
    @ToString.Exclude
    private ArrayList<Room> rooms;

    public Hotel(UUID uuid, String name, String country) {
        this.uuid = uuid.toString();
        this.name = name;
        this.country = country;
        this.rooms = new ArrayList<>();
    }
}
