package com.example.transportation.event.domain;

import jakarta.persistence.Entity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class FlightRemovedEvent extends FlightChangedEvent {

}
