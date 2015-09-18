package com.askdynamics.web;

import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Comment;
import com.askdynamics.dao.Question;
import com.askdynamics.persistence.QuestionPersistor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
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
    public void editQuestion(@QueryParam("_id") String id,
                             @QueryParam("username") String username,
                             @QueryParam("title") String title,
                             @QueryParam("body") String body,
                             @QueryParam("tags") String tags) {

        List tagList = tokenizeTags(tags);
        Question question = new Question(username, title, body, tagList);
        getQuestionPersistor().update(id, question);
    }

    @GET
    @Path("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllQuestions() {
        getQuestionPersistor().list();
    }

    @DELETE
    @Path("/delete")
    public void deleteQuestion(@QueryParam("_id") String id) {
        getQuestionPersistor().delete(id);
    }

    @PUT
    @Path("/answer")
    public void addAnswer(@QueryParam("_id") String id,
                          @QueryParam("username") String username,
                          @QueryParam("body") String body) {
        Answer answer = new Answer(username, body);
        getQuestionPersistor().addAnswer(id, answer);
    }

    @PUT
    @Path("/questioncomment")
    public void addQuestionComment(@QueryParam("_id") String id,
                                   @QueryParam("username") String username,
                                   @QueryParam("comment") String comment) {
        Comment commentObj = new Comment(username, comment);
        getQuestionPersistor().addQuestionComment(id, commentObj);
    }

    @PUT
    @Path("/answercomment")
    public void addAnswerComment(@QueryParam("_id") String id,
                                 @QueryParam("index") String arrIndex,
                                 @QueryParam("username") String username,
                                 @QueryParam("comment") String comment) {
        Comment commentObj = new Comment(username, comment);
        getQuestionPersistor().addAnswerComment(id, Integer.valueOf(arrIndex), commentObj);
    }

    @GET
    @Path("/ping")
    public String pingQuestionService() {
        return "Question Service is up";
    }

    /**
     * Util function to tokenize tag string
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
}
