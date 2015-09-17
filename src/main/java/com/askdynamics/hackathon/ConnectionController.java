package com.askdynamics.hackathon;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionController {
	
	public MongoClient getClient(){
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		return mongoClient;
	}
}
