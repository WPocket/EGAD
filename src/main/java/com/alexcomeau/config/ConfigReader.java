package com.alexcomeau.config;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigReader {
    public static Config readConfig() throws ConfigException {
        try {
            ObjectMapper om = new ObjectMapper();
            Config config = om.readValue(new File("config.json"), Config.class);
            testConfig(config);
            formatUrls(config);
            return config;
        } catch (ConfigException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigException("-1", e.getMessage(), e);
        }
    }

    public static void testConfig(Config config) throws ConfigException {
        if (config.getRelational().length == 0 && config.getKeyValue().length == 0) {
            throw new ConfigException("21", "No databases defined");
        }
    }

    private static void formatUrls(Config config) {
        for (int i = 0; i < config.getKeyValue().length; i++) {
            config.getKeyValue()[i] = formatUrl(config.getKeyValue()[i]);
        }
        for (int i = 0; i < config.getRelational().length; i++) {
            config.getRelational()[i] = formatUrl(config.getRelational()[i]);
        }
    }

    private static Database formatUrl(Database db) {
        String url = db.getUrl();
        url = url.replaceFirst("\\$\\{password\\}", db.getPassword())
                .replaceFirst("\\$\\{username\\}", db.getUsername()).replaceFirst("\\$\\{port\\}", db.getPort())
                .replaceFirst("\\$\\{ip\\}", db.getIp()).replaceFirst("\\$\\{db\\}", db.getUse());
        db.setUrl(url);
        return db;
    }

}
