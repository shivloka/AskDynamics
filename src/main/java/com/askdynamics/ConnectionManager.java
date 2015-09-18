package com.askdynamics;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager {

    private static volatile ConnectionManager INSTANCE;
    private MongoClient mongoClient;

    private ConnectionManager(){
        mongoClient = new MongoClient("54.71.167.109", 27017);
    }
    public static ConnectionManager getInstance(){
        if(INSTANCE == null){
            synchronized (ConnectionManager.class){
                if(INSTANCE == null)
                    INSTANCE = new ConnectionManager();
            }
        }
        return INSTANCE;
    }

    public MongoDatabase getDb() {
        return mongoClient.getDatabase("askdynamics");
    }
}
