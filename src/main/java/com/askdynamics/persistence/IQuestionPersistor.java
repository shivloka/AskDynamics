package com.askdynamics.persistence;

import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Comment;

public interface IQuestionPersistor extends IPersistor {
    public void addAnswer(String id, Answer answer);
    public void addQuestionComment(String id, Comment comment);
    public void addAnswerComment(String id, Integer index, Comment comment);
}
