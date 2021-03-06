package com.alexcomeau.rest.kv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.datatypes.BoolData;
import com.alexcomeau.rest.datatypes.LongData;
import com.alexcomeau.rest.datatypes.ReturnData;
import com.alexcomeau.rest.datatypes.StringData;
import com.alexcomeau.rest.datatypes.Table;
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
@RequestMapping("kv/{index}")
/**
 * KVRest
 */
public class KVRestIndex {
    @Operation(summary = "get key {key}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/get/key.{key}")
    // returns an arraylist or an error code
    public Serializable getKey(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        String res = "";
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.get(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<String>().setData(res);
    }

    @Operation(summary = "Set key {key} to have value {value}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/set/key.{key}/value.{value}")
    public Serializable setKey(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String value) {
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.set(key, value);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new RestError(ResponseCode.OK, "OK");
    }

    @Operation(summary = "Set key {key} to timeout after a given number of seconds {t}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("expire/key.{key}/t.{time}")
    public Serializable expire(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.setKeyExpire(key, t);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new RestError(ResponseCode.OK, "OK");
    }

    @Operation(summary = "Set key {key} to hold the string value {value} and set key to timeout after a given number of seconds {t}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("setex/key.{key}/value.{value}/t.{time}")
    public Serializable setEx(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String value, @PathVariable String time) {
        try {
            Long t = Long.parseLong(time);
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.setWExpire(key, value, t);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new RestError(ResponseCode.OK, "OK");

    }

    @Operation(summary = "in the key value database in the index {index}, increment key {key} by 1")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BoolData.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("exists/key.{key}")
    public Serializable exists(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        boolean res = false;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.exists(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<Boolean>().setData(res);
    }

    @Operation(summary = "in the key value database in the index {index}, increment key {key} by 1")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LongData.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("incr/key.{key}")
    public Serializable incr(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        long res = 0l;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.incr(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<Long>().setData(res);
    }
    
    @Operation(summary = "in the key value database in the index {index}, increment key {key} by increment amount {inc}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LongData.class)) }),
        @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("incrby/key.{key}/incr.{incr}")
    public Serializable incr(@PathVariable String index, HttpServletResponse response, @PathVariable String key,
            @PathVariable String incr) {
        long res = 0;
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.incrBy(key, Long.parseLong(incr));
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<Long>().setData(res);
    }

    @Operation(summary = "delete key {key} in database with index {index}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("del/key.{key}")
    public Serializable del(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            kv.delete(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new RestError(ResponseCode.OK, "OK");
    }

    @Operation(summary = "add multiple keys {key} (comma separated list) with values {value} (comma separated list) to database in position {index}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("addMul/key.{key}/value.{value}")
    public Serializable addMul(@PathVariable String index, HttpServletResponse response, @PathVariable String[] key,
            @PathVariable String[] value) {
        // construct a hashmap
        if (key.length != value.length) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED, "the length of the values list is different from the length of the keys list");
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
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new RestError(ResponseCode.OK, "OK");
    }

    @Operation(summary = "get multiple keys {key} (comma separated list) from database in index {index}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("getMul/key.{key}")
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
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<HashMap<String, String>>().setData(res);
    }

    @Operation(summary = "get the type of key {key} from database in index {index}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "executed successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
        @ApiResponse(responseCode = "304", description = "nothing changed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }),
        @ApiResponse(responseCode = "404", description = "not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/type/key.{key}")
    // returns an arraylist or an error code
    public Serializable getType(@PathVariable String index, HttpServletResponse response, @PathVariable String key) {
        String res = "";
        try {
            KeyValue kv = Main.kv.get(Integer.parseInt(index));
            res = kv.getType(key);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e1) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST, "unexpected integer value");
        } catch (IndexOutOfBoundsException e2){
            response.setStatus(ResponseCode.NOT_FOUND.code);
            return new RestError(ResponseCode.NOT_FOUND, "database at index " + index + " does not exist");
        }
        return new ReturnData<String>().setData(res);
    }

}
