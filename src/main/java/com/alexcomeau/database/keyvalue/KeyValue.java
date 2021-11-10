package com.alexcomeau.database.keyvalue;

import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.database.DatabaseException;

public interface KeyValue {
    public void Connect() throws DatabaseException;

    public void Disconnect() throws DatabaseException;

    public String get(String key) throws DatabaseException;
    
    public void set(String key, String value) throws DatabaseException;

    public void setKeyExpire(String key, long exp) throws DatabaseException;

    public void setWExpire(String key, String value, long expire) throws DatabaseException;

    public boolean exists(String key) throws DatabaseException;

    public long incr(String key) throws DatabaseException;
    public long incrBy(String key, long incr) throws DatabaseException;

    public void delete(String key) throws DatabaseException;

    public void addMultiple(HashMap<String, String> hMap) throws DatabaseException;

    public HashMap<String, String> getMultiple(ArrayList<String> get) throws DatabaseException;
    
    public String getType(String key) throws DatabaseException;
}
