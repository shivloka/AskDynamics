package com.askdynamics.web;

import com.askdynamics.Util.QuestionUtil;
import com.askdynamics.Util.StringUtil;
import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Comment;
import com.askdynamics.dao.Question;
import com.askdynamics.persistence.QuestionPersistor;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Path("/question")
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private QuestionPersistor questionPersistor = new QuestionPersistor();

    public QuestionPersistor getQuestionPersistor() {
        return questionPersistor;
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postQuestion(@QueryParam("title") String title,
                             @QueryParam("body") String body,
                             @QueryParam("tags") String tags,
                             @QueryParam("username") String username) {

        List tagList = tokenizeTags(tags);
        Question question = new Question(username, title, body, tagList);
        getQuestionPersistor().write(question);
    }

    @PUT
    @Path("/edit")
    public void editQuestion(@QueryParam("_id") String hexStr,
                             @QueryParam("username") String username,
                             @QueryParam("title") String title,
                             @QueryParam("body") String body,
                             @QueryParam("tags") String tags) {

        List tagList = tokenizeTags(tags);
        Question question = new Question(username, title, body, tagList);
        logger.info("New Title:" + title);
        logger.info("New Body:" + body);
        logger.info("New Tags:" + tags);

//        getQuestionPersistor().update(getObjectId(hexStr), question);
        getQuestionPersistor().update(hexStr, question);
    }

    @GET
    @Path("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericEntity<List<String>> getAllQuestions(@QueryParam("orderFieldName") String
                                                               orderFieldName) {
        List<String> questions;
        if (orderFieldName == null)
            orderFieldName = "date";

        questions = QuestionUtil.search(orderFieldName, 10);
        return new GenericEntity<List<String>>(questions) {
        };

    }

    @DELETE
    @Path("/delete")
    public void deleteQuestion(@QueryParam("_id") String hexStr) {
        getQuestionPersistor().delete(getObjectId(hexStr));
    }

    @PUT
    @Path("/answer")
    public void addAnswer(@QueryParam("_id") ObjectId id,
                          @QueryParam("username") String username,
                          @QueryParam("body") String body) {
        Answer answer = new Answer(username, body);
        getQuestionPersistor().addAnswer(id, answer);
    }

    @PUT
    @Path("/questioncomment")
    public void addQuestionComment(@QueryParam("_id") ObjectId id,
                                   @QueryParam("username") String username,
                                   @QueryParam("comment") String comment) {
        Comment commentObj = new Comment(username, comment);
        getQuestionPersistor().addQuestionComment(id, commentObj);
    }

    @PUT
    @Path("/answercomment")
    public void addAnswerComment(@QueryParam("_id") ObjectId id,
                                 @QueryParam("index") String arrIndex,
                                 @QueryParam("username") String username,
                                 @QueryParam("comment") String comment) {
        Comment commentObj = new Comment(username, comment);
        getQuestionPersistor().addAnswerComment(id, Integer.valueOf(arrIndex), commentObj);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray searchQuestions(@QueryParam("searchString") String searchString) {
        Collection<String> questions;

        if (searchString.startsWith("@")) {
            questions = QuestionUtil.searchByUserName(searchString.substring(1), 10);
        } else {
            questions = QuestionUtil.search(StringUtil.getAllCombination(searchString));
        }

        return new JSONArray(questions);
    }

    /**
     * Util function to tokenize tag string
     *
     * @param tags
     * @return List of tags
     */
    private List tokenizeTags(String tags) {
        List<String> tagList = tags != null &&
                tags.length() > 0 &&
                tags.indexOf(",") != -1 ?
                Arrays.asList(tags.split(",")) : Arrays.asList(tags);
        return tagList;
    }

    private ObjectId getObjectId(String hexStr) {
        String hexString = hexStr.substring(hexStr.indexOf("\"") + 1, hexStr.lastIndexOf("\""));
        return new ObjectId(hexString);
    }
}
