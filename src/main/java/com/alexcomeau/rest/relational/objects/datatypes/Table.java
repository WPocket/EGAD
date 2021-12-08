package com.alexcomeau.rest.relational.objects.datatypes;

import java.io.Serializable;
import java.util.HashMap;

public class Table implements Serializable{
    private HashMap<String, String> data = null;

    public HashMap<String,String> getData() {
        return this.data;
    }

    public Table setData(HashMap<String,String> table) {
        this.data = table;
        return this;
    }
}