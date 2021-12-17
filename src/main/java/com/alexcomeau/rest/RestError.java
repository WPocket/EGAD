package com.alexcomeau.rest;

import java.io.Serializable;

import com.alexcomeau.utils.ResponseCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "Error")
public class RestError implements Serializable{
    @JsonProperty
    public int code = 00;
    @JsonProperty
    ResponseCode rc;
    @JsonProperty
    public String message;
    public RestError(ResponseCode rc, String message){
        this.code = rc.code;
        this.rc = rc;
        this.message = message;
    }

    public ResponseCode getResponseCode(){
        return rc;
    }
}
