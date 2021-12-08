package com.alexcomeau.rest.relational.objects.datatypes;

import java.io.Serializable;

public class StringData implements Serializable{
    private String data = null;

    public String getData() {
        return this.data;
    }

    public StringData setData(String ret) {
        this.data = ret;
        return this;
    }
}