package pg.rsww.AccommodationService.command.entity.command;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class RemoveHotelCommand {
    @JsonProperty("uuid")
    private UUID uuid;
}