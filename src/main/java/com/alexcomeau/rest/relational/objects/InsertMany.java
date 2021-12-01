package com.alexcomeau.rest.relational.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.utils.Common;

public class InsertMany implements Serializable{
    private EntryPair[][] data;
    

    public void setData(EntryPair[][] data){
        this.data = data;
    }
    public EntryPair[][] getData(){
        return this.data;
    }

    private String table;

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

}
