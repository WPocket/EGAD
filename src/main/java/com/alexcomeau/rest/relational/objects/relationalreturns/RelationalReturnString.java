package com.alexcomeau.rest.relational.objects.relationalreturns;

import java.io.Serializable;

public class RelationalReturnString implements Serializable{
    private String data = null;

    public String getData() {
        return this.data;
    }

    public RelationalReturnString setData(String ret) {
        this.data = ret;
        return this;
    }
}