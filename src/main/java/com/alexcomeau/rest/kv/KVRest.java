package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.datatypes.ListBool;
import com.alexcomeau.rest.datatypes.ListLong;
import com.alexcomeau.rest.datatypes.ListString;
import com.alexcomeau.rest.datatypes.ListTable;
import com.alexcomeau.rest.datatypes.ReturnData;
import com.alexcomeau.utils.ResponseCode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("kv")
/**
 * KVRest
 */
public class KVRest {
    @Operation(summary = "get key {key} from every database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<String>>().setData(res);
    }
    @Operation(summary = "set key {key} to value {value} in every database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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

    @Operation(summary = "set key {key} to expire after time {t} seconds in every database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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

    @Operation(summary = "Set key {key} to hold the string value {value} and set key to timeout after a given number of seconds {t} in every database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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

    @Operation(summary = "does key {key} exist in every database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ListBool.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<Boolean>>().setData(res);
    }

    @Operation(summary = "increment key {key} in all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLong.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<Long>>().setData(res);
    }

    @Operation(summary = "increment key {key} by increment amount {inc} in all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLong.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<Long>>().setData(res);
    }

    @Operation(summary = "delete key {key} in all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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

    @Operation(summary = "add multiple keys {key} (comma separated list) with values {value} (comma separated list) to all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
    @Operation(summary = "get multiple keys {key} (comma separated list) from all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListTable.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<HashMap<String, String>>>().setData(res);
    }

    @Operation(summary = "get the type of key {key} from all databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
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
        return new ReturnData<ArrayList<String>>().setData(res);
    }

}
