package com.rsww.lydka.TripService.entity;

import lombok.*;
import java.util.ArrayList;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    private String id;
    private String uuid;
    private String name;
    private String country;
    private int stars;
    @ToString.Exclude
    private ArrayList<Room> rooms;
}