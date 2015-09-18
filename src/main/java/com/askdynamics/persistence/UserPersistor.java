package com.askdynamics.persistence;

import com.askdynamics.dao.Item;

import java.util.List;

public class UserPersistor implements IPersistor {

    public void write(Item item) {

    }

    @Override
    public Item read(String id) {
        return null;
    }

    @Override
    public void update(String id, Item item) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List list() {
        return null;
    }
}
