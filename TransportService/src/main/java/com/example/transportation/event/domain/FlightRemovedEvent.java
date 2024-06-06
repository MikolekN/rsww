package com.example.transportation.event.domain;

import jakarta.persistence.Entity;
import lombok.*;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FlightRemovedEvent extends FlightChangedEvent {

}
