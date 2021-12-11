package com.alexcomeau.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Common {
    public static ArrayList<Pair<String, String>> hashmapToPairList(HashMap<String, String> hashmap) {
        ArrayList<Pair<String, String>> output = new ArrayList<>();
        for (String key : hashmap.keySet()) {
            Pair<String, String> temp = new ImmutablePair<String,String>(key, hashmap.get(key));
            output.add(temp);
        }
        return output;
    }


}
