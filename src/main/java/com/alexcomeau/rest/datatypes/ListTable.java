package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ListTable implements Serializable{
    private ArrayList<HashMap<String, String>> data = null;

    public ArrayList<HashMap<String,String>> getData() {
        return this.data;
    }

    public ListTable setData(ArrayList<HashMap<String,String>> listTable) {
        this.data = listTable;
        return this;
    }
}