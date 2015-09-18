package com.askdynamics.persistence;

import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Comment;

public interface IQuestionPersistor extends IPersistor {
    public void addAnswer(Object id, Answer answer);

    public void addQuestionComment(Object id, Comment comment);

    public void addAnswerComment(Object id, Integer index, Comment comment);
}
