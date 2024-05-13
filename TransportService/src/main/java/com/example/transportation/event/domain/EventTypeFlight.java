package com.example.transportation.event.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event_type")
public class EventTypeFlight {

    @Id
    @Column(name = "event_id")
    private int eventId;

    @Column(name = "event_name")
    private String eventName;

}
