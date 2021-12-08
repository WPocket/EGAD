package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class List2DTable implements Serializable{
    private ArrayList<ArrayList<HashMap<String, String>>> data = null;

    public ArrayList<ArrayList<HashMap<String,String>>> getData() {
        return this.data;
    }

    public List2DTable setData(ArrayList<ArrayList<HashMap<String,String>>> list2dTable) {
        this.data = list2dTable;
        return this;
    }
}