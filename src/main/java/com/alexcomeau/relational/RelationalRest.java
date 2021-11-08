package com.alexcomeau.relational;

import java.util.ArrayList;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.database.relational.Relational;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rel")
/**
 * KVRest
 * */
public class RelationalRest {
    @GetMapping("/select/table={table}&value={value}")
    public ArrayList<ArrayList<String>> getKey(@PathVariable String table, @PathVariable String value) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        for(Relational rel : Main.relational){
            try{
                al.add(rel.select(value, table));
            }catch(DatabaseExecption e){
                e.printStackTrace();
            }
        }
        return al;
    }

}
