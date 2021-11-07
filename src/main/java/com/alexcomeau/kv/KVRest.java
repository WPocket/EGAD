package com.alexcomeau.kv;

import java.util.ArrayList;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kv/")
/**
 * KVRest
 * */
public class KVRest {
    @GetMapping("get/${key}")
	  public ArrayList<String> getKey(@RequestParam(value = "key") String key) {
        ArrayList<String> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.get(key));
            } catch (DatabaseExecption e) {
                e.printStackTrace();
                continue;
            }
        }
		    return res;
	  }

}
