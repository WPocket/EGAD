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
@RequestMapping("kv/{index}")
/**
 * KVRest
 */
public class KVRestIndex {
    @GetMapping("/get/key={key}")
    // returns an arraylist or an error code
    public Serializable getKey(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        String res = "";
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.get(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("/set/key={key}&value={value}")
    public Serializable setKey(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String value) {
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.set(key, value);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("expire/key={key}&t={time}")
    public Serializable expire(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.setKeyExpire(key, t);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("setex/key={key}&value={value}&t={time}")
    public Serializable setEx(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String value, @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.setWExpire(key, value, t);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return new RestError(ResponseCode.OK);

    }

    @GetMapping("exists/key={key}")
    public Serializable exists(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        boolean res = false;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.exists(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("incr/key={key}")
    public Serializable incr(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        long res = 0l;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.incr(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("incrby/key={key}&incr={incr}")
    public Serializable incr(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String incr) {
        long res = 0;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.incrBy(key, Long.parseLong(incr));
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("del/key={key}")
    public Serializable del(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.delete(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("addMul/key={key}&value={value}")
    public Serializable addMul(@PathVariable String index, HttpServletResponse response, @PathVariable String[] key,
            @PathVariable String[] value) {
        // construct a hashmap
        if (key.length != value.length) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
return new RestError(ResponseCode.NOT_MODIFIED);
        }
        HashMap<String, String> hMap = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            hMap.put(key[i], value[i]);
        }
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.addMultiple(hMap);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return new RestError(ResponseCode.OK);
    }

    @GetMapping("getMul/key={key}")
    public Serializable getMultiple(@PathVariable String index, HttpServletResponse response,
            @PathVariable String[] key) {
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, String> res = new HashMap<>();
        for (String s : key) {
            list.add(s);
        }
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.getMultiple(list);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("/type/key={key}")
    // returns an arraylist or an error code
    public Serializable getType(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        String res = "";
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.getType(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND);
        }
        return res;
    }

}
