package com.alexcomeau.rest.datatypes;

import java.io.Serializable;

public class BoolData implements Serializable{
    private Boolean data = null;

    public Boolean getData() {
        return this.data;
    }

    public BoolData setData(Boolean ret) {
        this.data = ret;
        return this;
    }
}