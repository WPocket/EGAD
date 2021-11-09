package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.RestResponse;

import org.apache.commons.lang3.tuple.Pair;
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

    @GetMapping("expire/key={key}&t={time}")
    public Serializable expire(@PathVariable String key, @PathVariable String time) {
        try{
            Long t = Long.parseLong(time);
            for (KeyValue kv : Main.kv) {
                kv.setKeyExpire(key, t);
            }
        } catch (DatabaseExecption e) {
            return new RestError(e.getCode(), e.getMessage());
        } catch (NumberFormatException e) {
            return new RestError("05", e.getMessage());
        }
        return new RestResponse("00", "OK");
    }

    @GetMapping("setex/key={key}&value={value}&t={time}")
    public Serializable setEx(@PathVariable String key, @PathVariable String value, @PathVariable String time) {
        try{
            Long t = Long.parseLong(time);
            for (KeyValue kv : Main.kv) {
                kv.setWExpire(key,value,t);
            }
        } catch (DatabaseExecption e) {
            return new RestError(e.getCode(), e.getMessage());
        } catch (NumberFormatException e) {
            return new RestError("05", e.getMessage());
        }
        return new RestResponse("00", "OK");

    }

    @GetMapping("exists/key={key}")
    public Serializable exists(@PathVariable String key) {
        ArrayList<Boolean> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.exists(key));
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return res;
    }

    @GetMapping("incr/key={key}")
    public Serializable incr(@PathVariable String key) {
        ArrayList<Long> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.incr(key));
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return res;
    }

    @GetMapping("incrby/key={key}&incr={incr}")
    public Serializable incr(@PathVariable String key, @PathVariable String incr) {
        ArrayList<Long> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.incrBy(key,Long.parseLong(incr)));
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            } catch (NumberFormatException e) {
                return new RestError("05", e.getMessage());
            }
        }
        return res;
    }


    @GetMapping("del/key={key}")
    public Serializable del(@PathVariable String key) {
        for (KeyValue kv : Main.kv) {
            try{
                kv.delete(key);
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return new RestResponse("00", "OK");
    }

    @GetMapping("addMul/key={key}&value={value}")
    public Serializable addMul(@PathVariable String[] key, @PathVariable String[] value) {
        //construct a hashmap
        if (key.length != value.length) {
            return new RestError("06", "mismatched array sizes");
        }
        HashMap<String, String> hMap = new HashMap<>();
        for(int i = 0; i < key.length; i++){
            hMap.put(key[i], value[i]);
        }

        for (KeyValue kv : Main.kv) {
            try{
                kv.addMultiple(hMap);
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return new RestResponse("00", "OK");
    }

    @GetMapping("getMul/key={key}")
    public Serializable getMultiple(@PathVariable String[] key) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        for (String s : key) {
            list.add(s);
        }
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.getMultiple(list));
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return res;
    }

    @GetMapping("/type/key={key}")
    //returns an arraylist or an error code
    public Serializable getType(@PathVariable String key) {
        ArrayList<String> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try{
                res.add(kv.getType(key));
            } catch (DatabaseExecption e) {
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return res;
    }

}
































