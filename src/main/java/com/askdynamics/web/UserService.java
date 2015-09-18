package com.askdynamics.web;

import com.askdynamics.ConnectionManager;
import com.askdynamics.dao.User;
import com.askdynamics.persistence.IPersistor;
import com.askdynamics.persistence.UserPersistor;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserService {

    MongoDatabase db;
    MongoCollection<Document> userCollection;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private IPersistor userPersistor = new UserPersistor();
    public IPersistor getUserPersistor(){
        return userPersistor;
    }

    protected void getCollection() {
        this.db = ConnectionManager.getInstance().getDb();
        this.userCollection = db.getCollection("User");

        logger.info("User collection is " + db.listCollectionNames());
    }


    @POST
    @Path("/insert")
    public String insertUser(@QueryParam(value = "email") String email, @QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {


        Document userDocument = new Document("_id", username).append("email", email).append("password", password);
        this.userCollection.insertOne(userDocument);
        return "Successfully added user";
    }

    @POST
    @Path("/update")
    public String updateUser(@QueryParam(value = "username") String username, @QueryParam(value = "email") String email, @QueryParam(value = "password") String password) {
        this.userCollection.updateOne(new Document("_id", username),
                new Document("$set", new Document("email", email)).append("password", password));

        return "Successfully updated user ";
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String selectUser(@QueryParam(value = "username") String username) {
        FindIterable<Document> userDetails = this.userCollection.find(new Document("_id", username));

        userDetails.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        return "Returned user";
    }

    @DELETE
    @Path("/remove")
    public String removeUser(@QueryParam(value = "username") String username) {
        this.userCollection.deleteOne(new Document("_id", username));
        return "Deleted User: " + username;
    }


}
