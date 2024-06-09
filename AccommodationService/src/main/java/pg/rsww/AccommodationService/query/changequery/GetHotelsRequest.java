package pg.rsww.AccommodationService.query.changequery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetHotelsRequest{
    @JsonProperty("uuid")
    private UUID uuid;
}
