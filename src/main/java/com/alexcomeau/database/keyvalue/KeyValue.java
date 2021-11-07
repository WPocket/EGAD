package com.alexcomeau.database.keyvalue;

import java.util.HashMap;
import java.util.List;

import com.alexcomeau.database.DatabaseExecption;

public interface KeyValue {
    public void Connect() throws DatabaseExecption;

    public void Disconnect() throws DatabaseExecption;

    public String get(String key) throws DatabaseExecption;
    
    public void set(String key, String value) throws DatabaseExecption;

    public void setKeyExpire(String key, long exp) throws DatabaseExecption;

    public void setWExpire(String key, String value, int expire) throws DatabaseExecption;

    public boolean exists(String key);

    public void incr(String key);
    public void incrBy(String key, int incr);

    public void delete(String key);

    public void addMultiple(HashMap<String, String> hMap);

    public HashMap<String, String> getMultiple(List<String> get);
    
    public String getType(String key);
}
