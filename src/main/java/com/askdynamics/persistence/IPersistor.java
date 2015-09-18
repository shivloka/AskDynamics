package com.askdynamics.persistence;

import com.askdynamics.dao.Item;

public interface IPersistor {

    public void write(Item item);
}
