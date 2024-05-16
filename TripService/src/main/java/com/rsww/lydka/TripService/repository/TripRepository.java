package com.rsww.lydka.TripService.repository;

import com.rsww.lydka.TripService.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findAllByUuidIn(Set<String> TripIds);
}
