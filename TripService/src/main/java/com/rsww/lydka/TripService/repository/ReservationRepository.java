package com.rsww.lydka.TripService.repository;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.rsww.lydka.TripService.storage.MongoDatabaseWrapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class ReservationRepository {

    private final MongoDatabaseWrapper mongoDatabaseWrapper;
    private final String collectionName = "trips_reservations";

    @Autowired
    public ReservationRepository(MongoDatabaseWrapper mongoDatabaseWrapper) {
        this.mongoDatabaseWrapper = mongoDatabaseWrapper;
    }

    public Set<Reservation> deleteOutdatedReservations() {
        final var currentDate = LocalDateTime.now();
        final var reservations = new HashSet<Reservation>();
        Document filter = new Document();
        filter.append("reserved", new Document("$lt", currentDate));
        filter.append("payed", false);


        mongoDatabaseWrapper.getDatabase().getCollection(collectionName, Reservation.class)
                .find(filter)
                .into(reservations);
        mongoDatabaseWrapper.getDatabase().getCollection(collectionName, Reservation.class)
                .deleteMany(filter);
        return reservations;
    }

    public void save(Reservation reservation) {
        if (reservation.getReservationId() == null) {
            final var uuid = UUID.randomUUID().hashCode();
            reservation.setReservationId(String.valueOf(uuid));
        }
        mongoDatabaseWrapper.getDatabase().getCollection(collectionName, Reservation.class)
                .insertOne(reservation);
    }

    public Set<Reservation> getUserReservations(Long userId) {
        final var results = new HashSet<Reservation>();
        final var query = new Document("userId", userId);

        mongoDatabaseWrapper.getDatabase().getCollection(collectionName, Reservation.class)
                .find(query)
                .into(results);
        return results;
    }

    public void markAsPayed(String reservationId) {
        final var query = new BasicDBObject();
        query.put("reservationId", reservationId);
        Document update = new Document("$set", new Document("payed", true));
        mongoDatabaseWrapper.getDatabase().getCollection(collectionName, Reservation.class)
                .updateOne(query, update);
    }

    public void deleteReservation(Long reservationId) {
        final var query = new BasicDBObject();
        query.put("reservationId", reservationId);
        mongoDatabaseWrapper.getDatabase()
                .getCollection(collectionName, Reservation.class)
                .deleteOne(query);
    }

    @Data
    @Builder
    @Jacksonized
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reservation {
        private String reservationId;
        private String startFlightReservation;
        private String endFlightReservation;
        private String startFlightId;
        private String endFlightId;
        private String userId;
        private String hotelReservation;
        private String tripId;
        private String hotelId;
        private Boolean payed;
        private LocalDateTime reserved;
        private Double price;
    }
}