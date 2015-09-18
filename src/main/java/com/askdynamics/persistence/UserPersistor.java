package com.askdynamics.persistence;

import com.askdynamics.dao.Item;

import java.util.List;

public class UserPersistor implements IPersistor {

    public void write(Item item) {

    }

    @Override
    public Item read(Object id) {
        return null;
    }

    @Override
    public void update(Object id, Item item) {

    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List list() {
        return null;
    }
}
