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
    public RestError(ResponseCode rc){
        this.code = rc.code;
        this.rc = rc;
    }

    public ResponseCode getResponseCode(){
        return rc;
    }
}
