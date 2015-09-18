package com.askdynamics.persistence;

import com.askdynamics.ConnectionManager;
import com.askdynamics.dao.Item;
import com.askdynamics.dao.Question;
import com.askdynamics.web.QuestionService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.java.Log;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionPersistor implements IPersistor {
    MongoDatabase db;

    private static final Logger logger = LoggerFactory.getLogger(QuestionPersistor.class);

    public QuestionPersistor() {
        db = ConnectionManager.getInstance().getDb();
    }

    protected MongoCollection getCollection() {
        return db.getCollection("Question");
    }

    @Override
    public void write(Item item) {
        Question question = (Question) item;
        logger.info(question.getUsername() + ":" + question.getTitle() + " " + question.getBody());

        Document questionDocument = new Document("username", question.getUsername())
                .append("title", question.getTitle())
                .append("body", question.getBody())
                .append("date", question.getDate())
                .append("views", question.getViews())
                .append("voteUps", question.getVoteUps())
                .append("voteDowns", question.getVoteDowns())
                .append("tags", question.getTags())
                .append("comments", question.getComments())
                .append("answers", question.getAnswers());

        getCollection().insertOne(questionDocument);
    }
}
