package com.alexcomeau.rest.datatypes;

import java.io.Serializable;



public class ReturnData<T extends Serializable> implements Serializable{
    private T data;

    public ReturnData<T> setData(T data){
        this.data = data;
        return this;
    }
    public T getData(){
        return this.data;
    }

    
}
