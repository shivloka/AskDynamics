package com.askdynamics;

import com.mongodb.MongoClient;

public class ConnectionManager {

    public MongoClient getClient() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return mongoClient;
    }
}
