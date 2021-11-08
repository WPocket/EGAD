package com.alexcomeau.rest;

import java.io.Serializable;

public class RestError implements Serializable{
    public String code = "";
    public String message = "";
    public RestError(String code, String message){
        this.code = code;
        this.message = message;
    }
}
