package com.askdynamics.persistence;

import com.askdynamics.dao.Answer;
import com.askdynamics.dao.Item;

import java.util.List;

public interface IPersistor {

    public void write(Item item);
    public Item read(String id);
    public void update(String id, Item item);
    public void delete(String id);
    public List list();
}
