package com.askdynamics.dao;

import lombok.Data;

import java.util.Date;
import java.util.List;

public
@Data
class Answer {
    private String username;
    private String body;
    private Date date;
    private Integer voteUps;
    private Integer voteDowns;
    private List<Comment> comments;

}
