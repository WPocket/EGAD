package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kv")
/**
 * KVRest
 * */
public class KVRest {
    @GetMapping("/get/{key}")
    public Serializable getKey(@PathVariable String key) {
    ArrayList<String> res = new ArrayList<>();
    for (KeyValue kv : Main.kv) {
        try{
            res.add(kv.get(key));
        } catch (DatabaseExecption e) {
            return new RestError(e.getCode(), e.getMessage());
        }
    }
        return res;
    }

}
