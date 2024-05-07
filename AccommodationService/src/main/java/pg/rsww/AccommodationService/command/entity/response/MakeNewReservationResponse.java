package pg.rsww.AccommodationService.command.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MakeNewReservationResponse {
    @JsonProperty("is_successful")
    private boolean isSuccessful;
    @JsonProperty("reservation_made_event")
    private ReservationMadeEvent reservationMadeEvent;
}
