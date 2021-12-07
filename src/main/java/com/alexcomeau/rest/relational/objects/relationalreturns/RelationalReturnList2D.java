package com.alexcomeau.rest.relational.objects.relationalreturns;

import java.io.Serializable;
import java.util.ArrayList;

public class RelationalReturnList2D implements Serializable{
    private ArrayList<ArrayList<String>> data = null;

    public ArrayList<ArrayList<String>> getData() {
        return this.data;
    }

    public RelationalReturnList2D setData(ArrayList<ArrayList<String>> list2d) {
        this.data = list2d;
        return this;
    }
}