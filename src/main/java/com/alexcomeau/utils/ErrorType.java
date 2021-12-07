package com.alexcomeau.utils;

public enum ErrorType {
    //server side
    NO_INTERNET,
    NOT_EXISTS,
    NOT_FOUND,
    INSERT_FAILED,
    SELECT_FAILED,
    DEL_FAILED,
    CANNOT_SORT,
    SERVER_CRASH_UNKNOWN,
    //client side
    NOT_AUTHORIZED,
    INVALID_KEY,
    EXPIRED_KEY,
    //bad input
    BAD_SQL,
    NUMBER_FORMAT,
    EMPTY_INPUT,
    BAD_FORMAT,
    LENGTH_MISMATCH,
    //bad config
    BAD_CONFIG,
    BAD_DATABASE,
    BAD_PASSWORD;
    
}
