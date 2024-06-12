package com.rsww.lydka.TripService.entity;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private String id;
    private String uuid;
    private int capacity;
    private String type;
    private float basePrice;
    private String hotelUuid;
}