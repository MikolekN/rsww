package com.rsww.mikolekn.APIGateway.country;

import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryRequest extends AbstractRequest {
    public CountryRequest(UUID uuid) {
        super(uuid);
    }
}
