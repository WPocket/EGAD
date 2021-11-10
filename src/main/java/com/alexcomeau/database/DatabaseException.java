package com.alexcomeau.database;

public class DatabaseException extends Exception{
    private String code;
    public DatabaseException(String code, String message){
        super(message);
        this.setCode(code);
    }
    
    public DatabaseException(String code, String message, Throwable cause) {
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
