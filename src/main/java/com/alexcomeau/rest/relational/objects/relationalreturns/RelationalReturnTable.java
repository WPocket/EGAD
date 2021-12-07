package com.alexcomeau.rest.relational.objects.relationalreturns;

import java.io.Serializable;
import java.util.HashMap;

public class RelationalReturnTable implements Serializable{
    private HashMap<String, String> data = null;

    public HashMap<String,String> getData() {
        return this.data;
    }

    public RelationalReturnTable setData(HashMap<String,String> table) {
        this.data = table;
        return this;
    }
}