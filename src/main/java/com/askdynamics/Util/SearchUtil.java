package com.askdynamics.Util;

import com.askdynamics.ConnectionManager;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

/**
 * Created by adwait.bhandare on 9/17/15.
 */
public class SearchUtil {

    private MongoDatabase getDatabaseObject() {
        return ConnectionManager.getInstance().getDb();
    }

    public Collection<String> search(List<String> searchlist){
        Iterator<String> strIterator = searchlist.iterator();
        MongoDatabase db = getDatabaseObject();

        DBObject regexQueryTitle = new BasicDBObject();
        DBObject regexQueryBody = new BasicDBObject();
        DBObject regexQueryComment = new BasicDBObject();
        DBObject regexQueryTags = new BasicDBObject();
        BasicDBList dbList = new BasicDBList();
        Map<String, String> stringDocumentMap = new HashMap<String, String>();


       while(strIterator.hasNext()){
           dbList.clear();
           String pattern = strIterator.next();
           regexQueryTitle.put("title",
                   new BasicDBObject("$regex", pattern)
                           .append("$options", "simx"));

           regexQueryComment.put("questionComments",
                   new BasicDBObject("$regex", pattern)
                           .append("$options", "simx"));

           regexQueryTags.put("tags",
                   new BasicDBObject("$regex", pattern)
                           .append("$options", "simx"));

           regexQueryBody.put("body",
                   new BasicDBObject("$regex", pattern)
                           .append("$options", "simx"));

           dbList.add(regexQueryTitle);
           dbList.add(regexQueryBody);
           dbList.add(regexQueryTags);
           dbList.add(regexQueryComment);

           BasicDBObject query = new BasicDBObject("$or", dbList);

           FindIterable<Document> documents =  db.getCollection("Question").find(query);
           for (Document document : documents) {
               System.out.println(document.toJson());
               String key = document.get("_id").toString();
               stringDocumentMap.put(document.get("_id").toString(), document.toJson());
           }

       }
        return stringDocumentMap.values();
     }
}
