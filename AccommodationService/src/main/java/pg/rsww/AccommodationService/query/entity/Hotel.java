package pg.rsww.AccommodationService.query.entity;

import jdk.jfr.Name;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Document("hotels")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    @Id
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
