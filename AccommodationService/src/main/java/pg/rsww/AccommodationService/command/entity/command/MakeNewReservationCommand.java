package pg.rsww.AccommodationService.command.entity.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pg.rsww.AccommodationService.query.entity.Hotel;
import pg.rsww.AccommodationService.query.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class MakeNewReservationCommand {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("room")
    private String roomType;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children")
    private int numberOfChildren;
    @JsonProperty("hotel")
    private UUID hotel;
}
