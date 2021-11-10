package com.alexcomeau.rest.relational;

import java.io.Serializable;
import java.util.ArrayList;

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

}
