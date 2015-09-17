package com.askdynamics.web;

import com.askdynamics.Persistor;
import com.askdynamics.dao.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

@Path("/question")
public class QuestionService {
    private Persistor persistor = new Persistor();
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @POST
    @Path("/post")
    public void postQuestion(@QueryParam("title") String title, @QueryParam("body") String body, @QueryParam("tags") String tags, @QueryParam("username") String username) {
        //persistor.write(new Question(username, title, body, tags))
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
