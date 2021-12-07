package com.alexcomeau.rest.relational.objects;

import java.io.Serializable;



public class RelationalReturn<T extends Serializable> implements Serializable{
    private T data;

    public RelationalReturn<T> setData(T data){
        this.data = data;
        return this;
    }
    public T getData(){
        return this.data;
    }

    
}
