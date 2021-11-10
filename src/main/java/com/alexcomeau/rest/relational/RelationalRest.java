package com.alexcomeau.rest.relational;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.relational.Relational;
import com.alexcomeau.rest.RestError;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
