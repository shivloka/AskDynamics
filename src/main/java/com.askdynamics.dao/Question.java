package com.askdynamics.dao;

import lombok.Data;

import java.util.Date;
import java.util.List;

public
@Data
class Question {
    private String username;
    private String title;
    private String body;
    private Date date;
    private Integer views;
    private Integer voteUps;
    private Integer voteDowns;
    private List<String> tags;
    private List<Comment> comments;
    private List<Answer> answers;

}