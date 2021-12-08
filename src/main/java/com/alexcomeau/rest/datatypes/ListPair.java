package com.alexcomeau.rest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

public class ListPair implements Serializable{
    private ArrayList<Pair<String, String>> data = null;

    public ArrayList<Pair<String, String>> getData() {
        return this.data;
    }

    public ListPair setData(ArrayList<Pair<String, String>> listPair) {
        this.data = listPair;
        return this;
    }
}