package com.askdynamics.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public
@Data
class Answer {
    private final String username;
    private final String body;
    private Date date;
    private Integer voteUps;
    private Integer voteDowns;
    private List<Comment> comments;

    public Answer(String username, String body){
        this.username = username;
        this.body = body;
        this.date = new Date();
        this.voteUps = 0;
        this.voteDowns = 0;
        this.comments = null;
    }

    public void voteUp(){
        this.voteUps = this.voteUps + 1;
    }

    public void voteDown(){
        this.voteDowns = this.voteDowns + 1;
    }

    public void addComment(Comment comment){
        if(this.comments == null)
                this.comments = new ArrayList<Comment>();
        comments.add(comment);
    }
}
