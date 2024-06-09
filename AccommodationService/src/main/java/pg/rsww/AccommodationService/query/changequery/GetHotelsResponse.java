package pg.rsww.AccommodationService.query.changequery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.AccommodationService.query.entity.Hotel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetHotelsResponse {
    @JsonProperty("hotels")
    List<Hotel> hotels;
}
