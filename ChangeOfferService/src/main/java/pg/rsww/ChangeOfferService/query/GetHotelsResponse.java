package pg.rsww.ChangeOfferService.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.ChangeOfferService.entity.Flight;
import pg.rsww.ChangeOfferService.entity.Hotel;

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
