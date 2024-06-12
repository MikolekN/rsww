package com.rsww.lydka.TripService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

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