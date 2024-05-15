package com.rsww.lydka.TripService.storage;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class MongoDatabaseWrapper {
    private final MongoClient mongoClient;
    private final String databaseName;

    public MongoDatabaseWrapper(MongoClient mongoClient, String databaseName) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
    }

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(
                    PojoCodecProvider.builder()
                            .automatic(true)
                            .build()
            )
    );

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
    }

    public void close() {
        mongoClient.close();
    }
}
