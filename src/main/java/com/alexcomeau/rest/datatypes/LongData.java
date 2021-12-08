package com.alexcomeau.rest.datatypes;

import java.io.Serializable;

public class LongData implements Serializable{
    private Long data = null;

    public Long getData() {
        return this.data;
    }

    public LongData setData(Long ret) {
        this.data = ret;
        return this;
    }
}