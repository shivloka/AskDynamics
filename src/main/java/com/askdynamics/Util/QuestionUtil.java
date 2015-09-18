package com.askdynamics.Util;

import com.askdynamics.ConnectionManager;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class QuestionUtil {

    private static final Logger logger = LoggerFactory.getLogger(QuestionUtil.class);

    private static MongoDatabase getDatabaseObject() {
        return ConnectionManager.getInstance().getDb();
    }

    public static Collection<String> search(List<String> searchlist) {
        Iterator<String> strIterator = searchlist.iterator();
        MongoDatabase db = getDatabaseObject();

        DBObject regexQueryTitle = new BasicDBObject();
        DBObject regexQueryBody = new BasicDBObject();
        DBObject regexQueryComment = new BasicDBObject();
        DBObject regexQueryTags = new BasicDBObject();
        BasicDBList dbList = new BasicDBList();
        Map<String, String> stringDocumentMap = new HashMap<String, String>();


        while (strIterator.hasNext()) {
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

            Document query = new Document("$or", dbList);

            FindIterable<Document> documents = db.getCollection("Question").find(query);
            for (Document document : documents) {
                System.out.println(document.toJson());
                String key = document.get("_id").toString();
                stringDocumentMap.put(document.get("_id").toString(), document.toJson());
            }

        }
        return stringDocumentMap.values();
    }

    public static List<String> search(String fieldName, int limit) {
        MongoDatabase db = getDatabaseObject();
        List<String> strings = new ArrayList<String>();
        FindIterable<Document> documents = db.getCollection("Question").find()
                .sort(new Document(fieldName, -1)).limit(limit);

        for (Document document : documents) {
            System.out.println(document.get(fieldName).toString());
            logger.info(document.toJson());
            strings.add(document.toJson());
        }

        return strings;
    }

    public static Collection<String> searchByUserName(String userName, int i) {
        MongoDatabase db = getDatabaseObject();
        ArrayList<String> strings = new ArrayList<String>();

        BasicDBObject query = new BasicDBObject();
        query.put("username", userName);
        FindIterable<Document> documents = db.getCollection("Question").find(query);
        for (Document document : documents) {
            System.out.println(document.get("username").toString());
            strings.add(document.toJson());
        }
        return strings;
    }
}
