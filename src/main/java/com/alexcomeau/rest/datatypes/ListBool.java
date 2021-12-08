package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

public class ListBool implements Serializable{
    private ArrayList<Boolean> data = null;

    public ArrayList<Boolean> getData() {
        return this.data;
    }

    public ListBool setData(ArrayList<Boolean> listPair) {
        this.data = listPair;
        return this;
    }
}