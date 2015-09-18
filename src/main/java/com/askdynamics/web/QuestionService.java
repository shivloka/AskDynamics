package com.askdynamics.web;

import com.askdynamics.dao.Question;
import com.askdynamics.persistence.IPersistor;
import com.askdynamics.persistence.QuestionPersistor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/question")
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private IPersistor questionPersistor = new QuestionPersistor();

    public IPersistor getQuestionPersistor() {
        return questionPersistor;
    }

    @POST
    @Path("/post")
    public void postQuestion(@QueryParam("title") String title, @QueryParam("body") String body, @QueryParam("tags") String tags, @QueryParam("username") String username) {
        List<String> tagList = tags != null && tags.length() > 0 && tags.indexOf(",") != -1 ? Arrays.asList(tags.split(",")) : Arrays.asList(tags);
        Question question = new Question(username, title, body, tagList);
        getQuestionPersistor().write(question);
    }

    @PUT
    @Path("/edit")
    public void editQuestion() {
    }

    @GET
    @Path("/questions")
    public void getAllQuestions() {
    }

    @DELETE
    @Path("/delete")
    public void deleteQuestion() {
    }

    @PUT
    @Path("/answer")
    public void addAnswer() {
    }

    @PUT
    @Path("/questioncomment")
    public void addQuestionComment() {
    }

    @PUT
    @Path("/answercomment")
    public void addAnswerComment() {
    }

    @GET
    @Path("/ping")
    public String pingQuestionService() {
        return "Question Service is up";
    }
}
