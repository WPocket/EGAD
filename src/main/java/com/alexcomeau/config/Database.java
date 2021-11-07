package com.alexcomeau.config;

public class Database {
    private String name;    
    private String url;
    private String username;
    private String password;
    private String use;
    private String port;
    private String supplier;

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUse() {
        return this.use;
    }
    
    public void setUse(String use) {
        this.use = use;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    
    
    
}
