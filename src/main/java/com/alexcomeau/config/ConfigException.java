package com.alexcomeau.config;

public class ConfigException extends Exception{
    private String code;
    public ConfigException(String code, String message){
        super(message);
        this.setCode(code);
    }
    
    public ConfigException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
