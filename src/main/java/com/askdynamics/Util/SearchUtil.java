package com.askdynamics.Util;

import com.askdynamics.ConnectionManager;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.Iterator;
import java.util.List;

/**
 * Created by adwait.bhandare on 9/17/15.
 */
public class SearchUtil {

    private MongoDatabase getDatabaseObject() {
        MongoClient dbClient = ConnectionManager.getInstance().getMongoClient();
        return dbClient.getDatabase("askdynamics");
    }

    public List<DBObject> search(List<String> searchlist,){
        Iterator<String> strIterator = searchlist.iterator();
        MongoDatabase db = getDatabaseObject();

       while(strIterator.hasNext()){
            String pattern = strIterator.next();
            DBCursor cursor=db.getCollection("Question").find( {
                    "or": [
            { title: { $regex: pattern, "options": 'simx' } },
            { body: { $regex: pattern, 'options': 'simx' } },
            { tags: { $regex: pattern, $options: 'simx' } },
            { comment: { $regex: pattern, $options: 'simx' } }
            ]
            } );
            cursor.Collection.distinct("_id");
        }


    return null;
    }
}
