package com.rsww.lydka.TripService.repository;

import com.rsww.lydka.TripService.storage.MongoDatabaseWrapper;
import com.rsww.lydka.TripService.entity.Trip;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TripRepository {
    private static final Logger log = LoggerFactory.getLogger(TripRepository.class);
    private final MongoDatabaseWrapper mongoDatabaseWrapper;
    private final String tripsCollection = "trips";

    @Autowired
    public TripRepository(final MongoDatabaseWrapper mongoDatabaseWrapper) {
        this.mongoDatabaseWrapper = mongoDatabaseWrapper;
    }

    public Optional<Trip> findTripById(final UUID tripId) {
        final var query = new Document("trips", tripId);

        return Optional.ofNullable(mongoDatabaseWrapper.getDatabase()
                .getCollection(tripsCollection, Trip.class)
                .find(query)
                .first());
    }

    public List<Trip> findAllTrips() {
        final var trips = new ArrayList<Trip>();

        mongoDatabaseWrapper.getDatabase()
                .getCollection(tripsCollection, Trip.class)
                .find()
                .into(trips);

        return trips;
    }

    public Trip save(Trip trip) {
        if (trip.getTripId() == null) {
            final var uuid = UUID.randomUUID().hashCode();
            trip.setTripId(String.valueOf(uuid));
        }

        try {
            mongoDatabaseWrapper.getDatabase()
                    .getCollection(tripsCollection, Trip.class)
                    .insertOne(trip);
        } catch (MongoWriteException ex) {
            log.error("Exception during writing to the database.", ex);
        }

        return trip;
    }

    public void delete(UUID tripId) {
        final var query = new BasicDBObject();
        query.put("tripId", tripId);
        mongoDatabaseWrapper.getDatabase()
                .getCollection(tripsCollection, Trip.class)
                .deleteOne(query);
    }

    public Set<Trip> findTrips(final Set<String> tripsIds) {
        final var query = new Document("tripId", new Document("$in", tripsIds));
        final var result = new HashSet<Trip>();
        mongoDatabaseWrapper.getDatabase()
                .getCollection(tripsCollection, Trip.class)
                .find(query)
                .into(result);
        return result;
    }
}
