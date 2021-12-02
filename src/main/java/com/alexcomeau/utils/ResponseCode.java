package com.alexcomeau.utils;

public enum ResponseCode {
    OK(200, "OK"),
    CREATED(201, "Created resource"),
    ACCEPTED(202, ""),
    NO_CONTENT(204, ""),
    NOT_MODIFIED(304, ""),
    BAD_REQUEST(400, ""),
    UNAUTHORIZED(401, ""),
    FORBIDDEN(403, ""),
    NOT_FOUND(404, ""),
    TOO_MANY_REQUESTS(429, ""),
    SERVICE_UNAVAILABLE(503, "");
    public final int code;
    public final String message;
    ResponseCode(int code, String message){
        this.code = code;
        this.message = message;
    }
    
}
