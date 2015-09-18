package com.askdynamics.web;

import com.askdynamics.Util.QuestionUtil;
import com.askdynamics.Util.StringUtil;
import com.askdynamics.dao.Question;
import com.askdynamics.persistence.IPersistor;
import com.askdynamics.persistence.QuestionPersistor;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
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
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getAllQuestions(@QueryParam("orderfieldName") String orderFieldName) {
        Collection<String> questions = null;
        if (orderFieldName == null)
                orderFieldName = "date";

        questions = QuestionUtil.search(orderFieldName, 10);

        return new JSONArray(questions);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray searchQuestions(@QueryParam("searchString") String searchString) {
        Collection<String> questions = null;

        if (searchString.startsWith("@")) {
            questions = QuestionUtil.searchByUserName(searchString.substring(1), 10);
        } else {
            questions = QuestionUtil.search(StringUtil.getAllCombination(searchString));
        }

        return new JSONArray(questions);
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
