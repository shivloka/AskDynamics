package com.askdynamics.persistence;

import com.askdynamics.dao.Item;

import java.util.List;

public interface IPersistor {

    public void write(Item item);

    public Item read(Object id);

    public void update(Object id, Item item);

    public void delete(Object id);

    public List list();
}
