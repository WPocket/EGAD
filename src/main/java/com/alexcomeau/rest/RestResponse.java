package com.alexcomeau.rest;

import java.io.Serializable;

public class RestResponse implements Serializable{
    public String code = "";
    public String message = "";
    public RestResponse(String code, String message){
        this.code = code;
        this.message = message;
    }
}
