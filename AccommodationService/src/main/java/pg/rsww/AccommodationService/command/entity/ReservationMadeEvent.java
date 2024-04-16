package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ReservationMadeEvent extends  ReservationEvent {
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children")
    private int numberOfChildren;
    @JsonProperty("hotel")
    private UUID hotel;
    @JsonProperty("room")
    private UUID room;
    public ReservationMadeEvent(UUID uuid, UUID reservationUuid,
                                LocalDateTime timeStamp,
                                LocalDate startDate, LocalDate endDate,
                                int numberOfAdults, int numberOfChildren,
                                UUID hotel, UUID room
                                ) {
        super(uuid, reservationUuid);
        this.timeStamp = timeStamp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.hotel = hotel;
        this.room = room;
        this.setEventType(1);
    }
}
