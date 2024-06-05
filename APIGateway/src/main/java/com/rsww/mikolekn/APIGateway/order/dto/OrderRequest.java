package com.rsww.mikolekn.APIGateway.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.*;

import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest extends AbstractRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("flightToUuid")
    private String flightToUuid;

    @JsonProperty("flightFromUuid")
    private String flightFromUuid;

    @JsonProperty("hotelUuid")
    private String hotelUuid;

    @JsonProperty("roomType")
    private String roomType;

    @JsonProperty("dateFrom")
    private String dateFrom;

    @JsonProperty("dateTo")
    private String dateTo;

    @JsonProperty("numberOfAdults")
    private String numberOfAdults;

    @JsonProperty("numberOfChildrenUnder10")
    private String numberOfChildrenUnder10;

    @JsonProperty("numberOfChildrenUnder18")
    private String numberOfChildrenUnder18;

    public OrderRequest (UUID uuid, String username, String flightToUuid, String flightFromUuid, String hotelUuid,String roomType, String dateFrom, String dateTo, String numberOfAdults, String numberOfChildrenUnder10, String numberOfChildrenUnder18) {
        super(uuid);
        this.username = username;
        this.flightToUuid = flightToUuid;
        this.flightFromUuid = flightFromUuid;
        this.hotelUuid = hotelUuid;
        this.roomType = roomType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildrenUnder10 = numberOfChildrenUnder10;
        this.numberOfChildrenUnder18 = numberOfChildrenUnder18;
    }
}
