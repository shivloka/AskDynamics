package com.askdynamics.web;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.askdynamics.ConnectionManager;
import com.askdynamics.dao.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("/user")
public class UserService {

    MongoClient dbClient;
    MongoDatabase db ;
    MongoCollection<Document> userCollection;
    User user = new User();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    protected void dbConnection(){
        this.dbClient = ConnectionManager.getInstance().getMongoClient();
        this.db = dbClient.getDatabase("askdynamics");
        this.userCollection = db.getCollection("User");

        logger.info("User collection is " +db.listCollectionNames());
    }



    @POST
    @Path("/insert")
    public String insertUser(@QueryParam(value="email") String email, @QueryParam(value="username") String username, @QueryParam(value="password") String password){
        dbConnection();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        BasicDBObjectBuilder.start().add("_id", user.getUsername()).add("email", user.getEmail()).add("password", user.getPassword());
        return "Successfully added user";
    }

    @POST
    @Path("/update")
    public String updateUser(@QueryParam(value="username") String username, @QueryParam(value="email") String email, @QueryParam(value="password") String password){
        dbConnection();
        user.setUsername(username);
        user.setEmail(email);
        this.userCollection.updateOne(new Document("_id", user.getUsername()),
                new Document("$set", new Document("email", email)).append("password", password));

        return "Successfully updated user ";
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String selectUser(@QueryParam(value="username") String username){
        dbConnection();
        user.setUsername(username);
        FindIterable<Document> userDetails = this.userCollection.find(new Document("_id", user.getUsername()));

        userDetails.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        return "Returned user";
    }

    @DELETE
    @Path("/remove")
    public String removeUser(@QueryParam(value="username") String username){
        dbConnection();
        user.setUsername(username);
        this.userCollection.deleteOne(new Document("_id", user.getUsername()));
        return "Deleted User: " +user.getUsername();
    }


}
