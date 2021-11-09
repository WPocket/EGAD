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

    public void setWExpire(String key, String value, long expire) throws DatabaseExecption;

    public boolean exists(String key) throws DatabaseExecption;

    public long incr(String key) throws DatabaseExecption;
    public long incrBy(String key, int incr) throws DatabaseExecption;

    public void delete(String key) throws DatabaseExecption;

    public void addMultiple(HashMap<String, String> hMap) throws DatabaseExecption;

    public HashMap<String, String> getMultiple(List<String> get) throws DatabaseExecption;
    
    public String getType(String key) throws DatabaseExecption;
}
