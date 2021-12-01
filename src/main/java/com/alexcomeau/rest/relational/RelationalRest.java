package com.alexcomeau.rest.relational;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.relational.Relational;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.relational.objects.EntryPair;
import com.alexcomeau.rest.relational.objects.Insert;
import com.alexcomeau.rest.relational.objects.InsertMany;
import com.alexcomeau.utils.Common;

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
    public Serializable getKey(@PathVariable String table, @PathVariable String value) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.select(value, table));
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/table={table}&value={value}&where={where}")
    public Serializable getKeyWhere(@PathVariable String table, @PathVariable String value, @PathVariable String where) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhere(value, table, where));
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }
    @GetMapping("/select/table={table}&value={value}&offset={offset}")
    public Serializable getKeyOffset(@PathVariable String table, @PathVariable String value, @PathVariable String offset) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectOffset(value, table, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/table={table}&value={value}&offset={offset}&where={where}")
    public Serializable getKeyOffsetWhere(@PathVariable String table, @PathVariable String value,
            @PathVariable String offset, @PathVariable String where) {
        ArrayList<String> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereOffset(value, table, where, Integer.parseInt(offset)));
            }catch(NumberFormatException e){
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/table={table}&multiple={value}")
    public Serializable getMultiple(@PathVariable String table, @PathVariable String[] value) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultiple(valueAList, table));
            }catch(NumberFormatException e){
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/table={table}&multiple={value}&where={where}")
    public Serializable getMultipleWhere(@PathVariable String table, @PathVariable String[] value, @PathVariable String where) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> valueAList = new ArrayList<>();
        for (String s : value) {
            valueAList.add(s);
        }
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMultipleWhere(valueAList, table, where));
            }catch(NumberFormatException e){
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/table={table}&multiple={value}&offset={offset}&where={where}")
    public Serializable getMultipleWhereOffset(@PathVariable String table, @PathVariable String[] value,
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
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }
    @GetMapping("/select/table={table}&multiple={value}&offset={offset}")
    public Serializable getMultipleOffset(@PathVariable String table, @PathVariable String[] value,
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
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    // MAX

    @GetMapping("/select/{max}/table={table}&value={value}")
    public Serializable getKeyMax(@PathVariable String table, @PathVariable String value, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectMax(value, table, maxI));
            }catch(NumberFormatException e){
                return new RestError("03", "invalid max");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/{max}/table={table}&value={value}&where={where}")
    public Serializable getKeyWhereMax(@PathVariable String table, @PathVariable String value, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereMax(value, table, where, maxI));
            }catch(NumberFormatException e){
                return new RestError("03", "invalid max");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }
    @GetMapping("/select/{max}/table={table}&value={value}&offset={offset}")
    public Serializable getKeyOffsetMax(@PathVariable String table, @PathVariable String value, @PathVariable String offset, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectOffsetMax(value, table, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/{max}/table={table}&value={value}&offset={offset}&where={where}")
    public Serializable getKeyOffsetWhereMax(@PathVariable String table, @PathVariable String value,
            @PathVariable String offset, @PathVariable String where, @PathVariable String max) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for(Relational rel : Main.relational){
            try{
                al.add(rel.selectWhereOffsetMax(value, table, where, Integer.parseInt(offset), maxI));
            }catch(NumberFormatException e){
                return new RestError("03", "offset or max has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}")
    public Serializable getMultipleMax(@PathVariable String table, @PathVariable String[] value, @PathVariable String max) {
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
                return new RestError("03", "invalid Max");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}&where={where}")
    public Serializable getMultipleWhere(@PathVariable String table, @PathVariable String[] value, @PathVariable String where, @PathVariable String max) {
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
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @GetMapping("/select/{max}/table={table}&multiple={value}&offset={offset}&where={where}")
    public Serializable getMultipleWhereOffset(@PathVariable String table, @PathVariable String[] value,
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
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }
    @GetMapping("/select/{max}/table={table}&multiple={value}&offset={offset}")
    public Serializable getMultipleOffset(@PathVariable String table, @PathVariable String[] value,
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
                return new RestError("03", "offset has to be an integer");
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return al;
    }

    @PostMapping("/insertOne")
    public Serializable insertOne(@RequestBody Insert data){
        System.out.println("table:" + data.getTable());
        ArrayList<Pair<String, String>> pairlist = Common.hashmapToPairList(Common.entryPairToHashMap(data.getData()));
        for(Relational rel : Main.relational){
            try{
                rel.insert(data.getTable(), pairlist);
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return new RestError("0", "OK");
    }

    @PostMapping("/insertMany")
    public Serializable insertMany(@RequestBody InsertMany data){
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
        for(EntryPair[] p : data.getData()){
            dataList.add(Common.entryPairToHashMap(p));
        }
        for(Relational rel : Main.relational){
            try{
                rel.insertMany(data.getTable(), dataList);
            }catch(DatabaseException e){
                return new RestError(e.getCode(), e.getMessage());
            }
        }
        return new RestError("0", "OK");
    }
}
