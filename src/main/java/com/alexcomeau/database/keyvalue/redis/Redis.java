package com.alexcomeau.database.keyvalue.redis;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.keyvalue.KeyValue;

import redis.clients.jedis.Jedis;

public class Redis implements KeyValue{
    private Jedis jedis;

    public Redis(Database db){
        jedis = new Jedis(URI.create(db.getUrl()));
    }

    @Override
    public void Connect() throws DatabaseExecption {
        if(!jedis.isConnected()){
            jedis.connect();
        }
    }

    @Override
    public void Disconnect() throws DatabaseExecption {
        jedis.connect();
        
        if(jedis == null){
            return;
        }
        if(jedis.isConnected()){
            jedis.disconnect();
        }
        return;
    }

    @Override
    public String get(String key) throws DatabaseExecption {
        jedis.connect();
        String out;
        try{
            out = jedis.get(key);
            if(out == null){
                return "";
            }
            if(out.equals("nil")){
                throw new DatabaseExecption("20", "value does not exist");
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        return out;
    }

    @Override
    public void set(String key, String value) throws DatabaseExecption {
        jedis.connect();
        String reply;
        try{
            reply = jedis.set(key, value);
            if(reply.equals("OK")){
                return;
            }if(reply.equals("nil")){
                throw new DatabaseExecption("10", "value was not set");
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
    }

    @Override
    public void setKeyExpire(String key, long exp) throws DatabaseExecption {
        jedis.connect();
        try{
            double rep = jedis.expire(key, exp);
            if(rep == 0){
                throw new DatabaseExecption("11", "key was not set");
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
    }

    @Override
    public void setWExpire(String key, String value, long expire) throws DatabaseExecption {
        jedis.connect();
        try{
            String reply = jedis.setex(key, expire, value);
            if(reply.equals("OK")){
                return;
            }if(reply.equals("nil")){
                throw new DatabaseExecption("10", "value was not set");
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1", e.getMessage(), e);
        }
        
    }

    @Override
    public boolean exists(String key) throws DatabaseExecption {
        jedis.connect();
        try{
            boolean rep = jedis.exists(key);
            return rep;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
    }

    @Override
    public long incr(String key) throws DatabaseExecption {
        jedis.connect();
        try{
            long rep = jedis.incr(key);
            return rep;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        
    }

    @Override
    public long incrBy(String key, int incr) throws DatabaseExecption {
        jedis.connect();
        try{
            long rep = jedis.incrBy(key, incr);
            return rep;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        
        
    }

    @Override
    public void delete(String key) throws DatabaseExecption  {
        jedis.connect();
        try{
            long rep = jedis.del();
            if(rep != 1){
                throw new DatabaseExecption("11", "key was not removed");
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        
    }

    @Override
    public void addMultiple(HashMap<String, String> hMap) throws DatabaseExecption {
        jedis.connect();
        try{
            String errorKeys = "";
            for(String key :  hMap.keySet()){
                try{
                    this.set(key, hMap.get(key));
                }catch(DatabaseExecption e){
                    errorKeys += key + " ";
                }
            }
            if(!errorKeys.equals("")){
                throw new DatabaseExecption("12", "was unable to set: " + errorKeys);
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        
        
    }

    @Override
    public HashMap<String, String> getMultiple(List<String> get) throws DatabaseExecption  {
        jedis.connect();
        HashMap<String, String> hMap = new HashMap<>();
        try{
            String errorKeys = "";
            for(String s : get){
                try{
                    String addme = this.get(s);
                    hMap.put(s, addme);
                }catch(DatabaseExecption e){
                    errorKeys += s + " ";
                }
            }
            if(hMap.size() != 0){
                return hMap;
            }
            if(!errorKeys.equals("")){
                throw new DatabaseExecption("12", "was unable to set: " + errorKeys);
            }
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public String getType(String key) throws DatabaseExecption {
        jedis.connect();
        try{
            String rep = jedis.type(key);
            if(rep == "none"){
                throw new DatabaseExecption("12", "key does not exist");
            }
            return rep;
        }catch(DatabaseExecption e){
            throw e;
        }catch(Exception e){
            throw new DatabaseExecption("1",e.getMessage(), e);
        }
    }
    
}
