package com.alexcomeau.rest.relational;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.relational.Relational;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.relational.objects.EntryPair;
import com.alexcomeau.rest.relational.objects.relationalreturns.RelationalReturnList;
import com.alexcomeau.rest.relational.objects.relationalreturns.RelationalReturnList2D;
import com.alexcomeau.rest.relational.objects.relationalreturns.RelationalReturnList2DTable;
import com.alexcomeau.rest.relational.objects.relationalreturns.RelationalReturnListTable;
import com.alexcomeau.utils.Common;
import com.alexcomeau.utils.ResponseCode;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rel")
/**
 * KVRest
 * */
public class RelationalRest {
    @GetMapping("/select/table={table}&value={value}")
    public Serializable getKey(@PathVariable String table, HttpServletResponse response,@PathVariable String value) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.select(value, table));
            }catch(DatabaseException e){
                
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList().setData(al);
    }

    @GetMapping("/select/table={table}&value={value}&where={where}")
    public Serializable getKeyWhere(@PathVariable String table, HttpServletResponse response,@PathVariable String value, @PathVariable String where) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhere(value, table, where));
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList().setData(al);
    }
    @GetMapping("/select/table={table}&value={value}&offset={offset}")
    public Serializable getKeyOffset(@PathVariable String table, HttpServletResponse response, @PathVariable String value, @PathVariable String offset) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectOffset(value, table, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList().setData(al);
    }

    @GetMapping("/select/table={table}&value={value}&offset={offset}&where={where}")
    public Serializable getKeyOffsetWhere(@PathVariable String table, HttpServletResponse response,@PathVariable String value,
            @PathVariable String offset, @PathVariable String where) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereOffset(value, table, where, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList().setData(al);
    }

    @GetMapping("/select/table={table}&multiple={value}")
    public Serializable getMultiple(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultiple(valueAList, table));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnListTable().setData(al);
    }

    @GetMapping("/select/table={table}&multiple={value}&where={where}")
    public Serializable getMultipleWhere(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value, @PathVariable String where) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleWhere(valueAList, table, where));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnListTable().setData(al);
    }

    @GetMapping("/select/table={table}&multiple={value}&offset={offset}&where={where}")
    public Serializable getMultipleWhereOffset(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value,
            @PathVariable String offset, @PathVariable String where) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleWhereOffset(valueAList, table, where, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnListTable().setData(al);
    }
    @GetMapping("/select/table={table}&multiple={value}&offset={offset}")
    public Serializable getMultipleOffset(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value,
            @PathVariable String offset) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleOffset(valueAList, table, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnListTable().setData(al);
    }

    // MAX

    @GetMapping("/select/{max}/table={table}&value={value}")
    public Serializable getKeyMax(@PathVariable String table, HttpServletResponse response,@PathVariable String value, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMax(value, table, maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2D().setData(al);
    }

    @GetMapping("/select/{max}/table={table}&value={value}&where={where}")
    public Serializable getKeyWhereMax(@PathVariable String table, HttpServletResponse response,@PathVariable String value, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereMax(value, table, where, maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2D().setData(al);
    }
    @GetMapping("/select/{max}/table={table}&value={value}&offset={offset}")
    public Serializable getKeyOffsetMax(@PathVariable String table, HttpServletResponse response,@PathVariable String value, @PathVariable String offset, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectOffsetMax(value, table, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2D().setData(al);
    }

    @GetMapping("/select/{max}/table={table}&value={value}&offset={offset}&where={where}")
    public Serializable getKeyOffsetWhereMax(@PathVariable String table, HttpServletResponse response,@PathVariable String value,
            @PathVariable String offset, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereOffsetMax(value, table, where, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2D().setData(al);
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}")
    public Serializable getMultipleMax(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value, @PathVariable String max) {
        ArrayList<ArrayList<HashMap<String, String>>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleMax(valueAList, table, maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2DTable().setData(al);
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}&where={where}")
    public Serializable getMultipleWhere(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<HashMap<String, String>>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleWhereMax(valueAList, table, where, maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2DTable().setData(al);
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}&offset={offset}&where={where}")
    public Serializable getMultipleWhereOffset(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value,
            @PathVariable String offset, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<HashMap<String, String>>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleWhereOffsetMax(valueAList, table, where, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return new RelationalReturnList2DTable().setData(al);
    }
    @GetMapping("/select/{max}/table={table}&multiple={value}&offset={offset}")
    public Serializable getMultipleOffset(@PathVariable String table, HttpServletResponse response,@PathVariable String[] value,
            @PathVariable String offset, @PathVariable String max) {
        ArrayList<ArrayList<HashMap<String, String>>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleOffsetMax(valueAList, table, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.BAD_REQUEST.code);
                return new RestError(ResponseCode.BAD_REQUEST);
            }
        }
        return al;
    }

    @PostMapping("/insertOne/{table}")
    public Serializable insertOne(@RequestBody EntryPair[] data, @PathVariable String table, HttpServletResponse response){
        ArrayList<Pair<String, String>> pairlist = Common.hashmapToPairList(Common.entryPairToHashMap(data));
        for(Relational rel : Main.relational){
            try{
                rel.insert(table, pairlist);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.NOT_MODIFIED.code);
                return new RestError(ResponseCode.NOT_MODIFIED);
            }
        }
        return new RestError(ResponseCode.OK);
    }

    @PostMapping("/insertMany/{table}")
    public Serializable insertMany(@RequestBody EntryPair[][] data, @PathVariable String table, HttpServletResponse response){
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
        for(EntryPair[] p : data){
            dataList.add(Common.entryPairToHashMap(p));
        }
        for(Relational rel : Main.relational){
            try{
                rel.insertMany(table, dataList);
            }catch(DatabaseException e){
                response.setStatus(ResponseCode.NOT_MODIFIED.code);
                return new RestError(ResponseCode.NOT_MODIFIED);
            }
        }
        return new RestError(ResponseCode.OK);
    }
}
