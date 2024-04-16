package pg.rsww.AccommodationService.query.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
@Document("reservations")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    private String id;
    private String uuid;
    private LocalDateTime timeStamp;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hotelUuid;
    private String roomUuid;
    public Reservation(String uuid, LocalDateTime timeStamp, LocalDate startDate, LocalDate endDate, String hotelUuid, String roomUuid) {
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotelUuid = hotelUuid;
        this.roomUuid = roomUuid;
    }
}
