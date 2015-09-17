package com.askdynamics.dao;

import lombok.Data;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {
    private final String username;
    private String title;
    private String body;
    private Date date;
    private Integer views;
    private Integer voteUps;
    private Integer voteDowns;
    private List<String> tags;
    private List<Comment> comments;
    private List<Answer> answers;

    public Question(String username, String title, String body, List<String> tags){
        this.username = username;
        this.title = title;
        this.body = body;
        this.date = new Date();
        this.views = 0;
        this.voteUps = 0;
        this.voteDowns = 0;
        this.tags = tags;
        this.comments = new ArrayList<Comment>();
        this.answers = new ArrayList<Answer>();
    }

}
