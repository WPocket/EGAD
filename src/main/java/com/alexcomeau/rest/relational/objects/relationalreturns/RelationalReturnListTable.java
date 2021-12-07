package com.alexcomeau.rest.relational.objects.relationalreturns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RelationalReturnListTable implements Serializable{
    private ArrayList<HashMap<String, String>> data = null;

    public ArrayList<HashMap<String,String>> getData() {
        return this.data;
    }

    public RelationalReturnListTable setData(ArrayList<HashMap<String,String>> listTable) {
        this.data = listTable;
        return this;
    }
}