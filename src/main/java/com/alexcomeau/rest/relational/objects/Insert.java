package com.alexcomeau.rest.relational.objects;

import java.io.Serializable;

public class Insert implements Serializable{
    private EntryPair[] data;

    public EntryPair[] getData() {
        return this.data;
    }

    public void setData(EntryPair[] data) {
        this.data = data;
    };

    private String table;
    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
