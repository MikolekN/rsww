package pg.rsww.OfferService.query.offerchange.hotel;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event implements Serializable {
    private UUID uuid;
    private LocalDateTime timeStamp;
}
