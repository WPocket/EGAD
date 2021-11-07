package com.alexcomeau;

import java.util.Collections;

import com.alexcomeau.config.Config;
import com.alexcomeau.config.ConfigReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static Config config;
    public static void main(String[] args) {
        try{
            System.out.println(System.getProperty("user.dir"));
            config = ConfigReader.readConfig();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", (config.getPort() <= 0)? "8080" : config.getPort()));
        app.run(args);
    }

}
