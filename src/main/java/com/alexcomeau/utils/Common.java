package com.alexcomeau.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.rest.relational.objects.EntryPair;

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

    public static HashMap<String, String> entryPairToHashMap(EntryPair[] data){
        HashMap<String, String> output = new HashMap<>();
        if(data.length == 0){
            return output;
        }
        for(EntryPair pair : data){
            output.put(pair.getKey(), pair.getValue());
        }

        return output;
    }

}
