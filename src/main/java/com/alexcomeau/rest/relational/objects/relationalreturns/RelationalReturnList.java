package com.alexcomeau.rest.relational.objects.relationalreturns;

import java.io.Serializable;
import java.util.ArrayList;

public class RelationalReturnList implements Serializable{
    private ArrayList<String> data = null;

    public ArrayList<String> getData() {
        return this.data;
    }

    public RelationalReturnList setData(ArrayList<String> list) {
        this.data = list;
        return this;
    }
}