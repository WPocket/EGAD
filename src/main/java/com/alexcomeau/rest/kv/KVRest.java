package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.RestResponse;

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
    @GetMapping("/get/key={key}")
    //returns an arraylist or an error code
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

    @GetMapping("/set/key={key}&value={value}")
    public Serializable setKey(@PathVariable String key, @PathVariable String value) {
        for (KeyValue kv : Main.kv) {
            try{
                kv.set(key, value);
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return new RestResponse("00", "OK");
    }

}
