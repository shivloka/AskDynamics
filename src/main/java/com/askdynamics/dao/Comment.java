package com.askdynamics.dao;

import lombok.Data;

public
@Data
class Comment {
    private String username;
    private String comment;

    public Comment(String username, String comment){
        this.username = username;
        this.comment = comment;
    }
}
