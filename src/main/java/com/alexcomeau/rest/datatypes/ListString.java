package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

public class ListString implements Serializable{
    private ArrayList<String> data = null;

    public ArrayList<String> getData() {
        return this.data;
    }

    public ListString setData(ArrayList<String> list) {
        this.data = list;
        return this;
    }
}