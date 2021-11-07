package com.alexcomeau;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alexcomeau.config.Config;
import com.alexcomeau.config.ConfigReader;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KVFactory;
import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.database.relational.Relational;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static Config config;
    public static ArrayList<KeyValue> kv;
    public static ArrayList<Relational> relational;
    public static void main(String[] args) {
        kv = new ArrayList<>();
        relational = new ArrayList<>();
        try{
            System.out.println(System.getProperty("user.dir"));
            config = ConfigReader.readConfig();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", (config.getPort() <= 0)? "8080" : config.getPort()));
        KVFactory kvf = new KVFactory();
        kv.add(kvf.parseDB(config.getKeyValue()[0]));
        try{
            for(KeyValue keyValue: kv){
                System.out.println(keyValue.get("test"));
            }
        } catch (DatabaseExecption e) {
            e.printStackTrace();
        }
        //app.run(args);
        
    }

}
