package com.alexcomeau.rest;

import java.io.Serializable;

import com.alexcomeau.utils.ResponseCode;

public class RestError implements Serializable{
    public int code = 00;
    ResponseCode rc;
    public RestError(ResponseCode rc){
        this.code = rc.code;
        this.rc = rc;
    }

    public ResponseCode getResponseCode(){
        return rc;
    }
}
