package com.askdynamics;

import com.mongodb.MongoClient;

public class ConnectionManager {

    private static volatile ConnectionManager INSTANCE;
    private MongoClient mongoClient;

    private ConnectionManager() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    public static ConnectionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new ConnectionManager();
            }
        }
        return INSTANCE;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}