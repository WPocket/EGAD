package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.utils.ResponseCode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kv")
/**
 * KVRest
 */
public class KVRest {
    @GetMapping("/get/key.{key}")
    // returns an arraylist or an error code
    public Serializable getKey(@PathVariable String key, HttpServletResponse response) {
        ArrayList<String> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.get(key));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return res;
    }

    @GetMapping("/set/key.{key}/value.{value}")
    public Serializable setKey(@PathVariable String key, HttpServletResponse response, @PathVariable String value) {
        for (KeyValue kv : Main.kv) {
            try {
                kv.set(key, value);
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("expire/key.{key}/t.{time}")
    public Serializable expire(@PathVariable String key, HttpServletResponse response, @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            for (KeyValue kv : Main.kv) {
                kv.setKeyExpire(key, t);
            }
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED);
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("setex/key.{key}/value.{value}/t.{time}")
    public Serializable setEx(@PathVariable String key, HttpServletResponse response, @PathVariable String value,
            @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            for (KeyValue kv : Main.kv) {
                kv.setWExpire(key, value, t);
            }
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED);
        }
        return new RestError(ResponseCode.OK);

    }

    @GetMapping("exists/key.{key}")
    public Serializable exists(@PathVariable String key, HttpServletResponse response) {
        ArrayList<Boolean> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.exists(key));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return res;
    }

    @GetMapping("incr/key.{key}")
    public Serializable incr(@PathVariable String key, HttpServletResponse response) {
        ArrayList<Long> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.incr(key));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.NOT_MODIFIED.code);
                return new RestError(ResponseCode.NOT_MODIFIED);
            }
        }
        return res;
    }

    @GetMapping("incrby/key.{key}/incr.{incr}")
    public Serializable incr(@PathVariable String key, HttpServletResponse response, @PathVariable String incr) {
        ArrayList<Long> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.incrBy(key, Long.parseLong(incr)));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            } catch (NumberFormatException e) {
                response.setStatus(ResponseCode.NOT_MODIFIED.code);
                return new RestError(ResponseCode.NOT_MODIFIED);
            }
        }
        return res;
    }

    @GetMapping("del/key.{key}")
    public Serializable del(@PathVariable String key, HttpServletResponse response) {
        for (KeyValue kv : Main.kv) {
            try {
                kv.delete(key);
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.NOT_MODIFIED.code);
                return new RestError(ResponseCode.NOT_MODIFIED);
            }
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("addMul/key.{key}/value.{value}")
    public Serializable addMul(@PathVariable String[] key, HttpServletResponse response, @PathVariable String[] value) {
        // construct a hashmap
        if (key.length != value.length) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED);
        }
        HashMap<String, String> hMap = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            hMap.put(key[i], value[i]);
        }

        for (KeyValue kv : Main.kv) {
            try {
                kv.addMultiple(hMap);
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("getMul/key.{key}")
    public Serializable getMultiple(@PathVariable String[] key, HttpServletResponse response) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        for (String s : key) {
            list.add(s);
        }
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.getMultiple(list));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return res;
    }

    @GetMapping("/type/key.{key}")
    // returns an arraylist or an error code
    public Serializable getType(@PathVariable String key, HttpServletResponse response) {
        ArrayList<String> res = new ArrayList<>();
        for (KeyValue kv : Main.kv) {
            try {
                res.add(kv.getType(key));
            } catch (DatabaseException e) {
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return res;
    }

}
