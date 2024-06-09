package pg.rsww.AccommodationService.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Event implements Serializable {
    @Id
    private UUID uuid;
    private LocalDateTime timeStamp;
}
