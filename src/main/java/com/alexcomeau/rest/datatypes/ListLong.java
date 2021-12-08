package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

public class ListLong implements Serializable{
    private ArrayList<Long> data = null;

    public ArrayList<Long> getData() {
        return this.data;
    }

    public ListLong setData(ArrayList<Long> listPair) {
        this.data = listPair;
        return this;
    }
}