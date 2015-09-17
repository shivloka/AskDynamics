package com.askdynamics;

import com.askdynamics.dao.Question;
import com.mongodb.MongoClient;

public class Persistor {
    MongoClient client;

    public Persistor(){
        client = ConnectionManager.getInstance().getMongoClient();
    }

    public void write(Question question){

    }
}
