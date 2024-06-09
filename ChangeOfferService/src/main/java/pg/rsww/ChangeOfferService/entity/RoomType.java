package pg.rsww.ChangeOfferService.entity;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType {
    private String type;
    private float basePrice;
}
