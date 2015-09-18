package com.askdynamics.persistence;

import com.askdynamics.ConnectionManager;
import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Comment;
import com.askdynamics.dao.Item;
import com.askdynamics.dao.Question;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuestionPersistor implements IQuestionPersistor {
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

    @Override
    public Item read(Object id) {

        return null;
    }

    public void update(Object id, Item item) {
        Question question = (Question) item;
        ObjectId id1 = (ObjectId) id;
        logger.info(question.toString());
        logger.info("ObjectId: " + id);

        UpdateResult res = db.getCollection("question").updateOne(new Document("_id", id1),
                new Document("$set", new Document("title", question.getTitle()))
                        .append("$set", new Document("body", question.getBody()))
                        .append("$set", new Document("tags", question.getTags())));
        logger.info("UpdateResult: " + res.toString());
        logger.info("Stats: " + res.getMatchedCount() + ", " + res.getModifiedCount() + ", " + res
                .wasAcknowledged());
    }

    @Override
    public void delete(Object id) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", (ObjectId) id);
        db.getCollection("Question").deleteOne(document);
    }

    @Override
    public List list() {
//        final List<Question> questions = new ArrayList<Question>();
//        FindIterable<Document> iterable = db.getCollection("question").find();
        return null;
    }

    public void addAnswer(Object id, Answer answer) {
        Question question = (Question) read(id);
        ObjectId id1 = (ObjectId) id;
        question.addAnswer(answer);
        db.getCollection("question").updateOne(new Document("_id", id1),
                new Document("$set", new Document("answers", question.getAnswers())));
    }

    @Override
    public void addAnswerComment(Object id, Integer index, Comment comment) {
        Question question = (Question) read(id);
        try {
            question.getAnswer(index)
                    .addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addQuestionComment(Object id, Comment comment) {
        Question question = (Question) read(id);
        question.addComment(comment);
        db.getCollection("question").updateOne(new Document("_id", id),
                new Document("$set", new Document("questionComments", question.getComments())));

    }

}
