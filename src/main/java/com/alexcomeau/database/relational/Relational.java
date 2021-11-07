package com.alexcomeau.database.relational;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

public interface Relational {
    public void connect();
    public void disconnect();

    public void insert(String table, HashMap<String, Pair<String, String>> values);
    
    public ArrayList<String> select(String value);

    public ArrayList<HashMap<String, String>> selectMultiple(ArrayList<String> multiple);

    public ArrayList<String> selectWhere(String single, HashMap<String, String> where);
    public ArrayList<HashMap<String, String>> selectMultipleWhere(ArrayList<String> multiple, HashMap<String, String> where);

    public boolean newTable(String name, ArrayList<String> types, HashMap<String, String> options);
}
