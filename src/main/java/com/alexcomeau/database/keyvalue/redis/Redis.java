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
        // TODO Auto-generated method stub
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
            throw new DatabaseExecption("1", "unknown error");
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
            throw e;
        }
    }

    @Override
    public void setKeyExpire(String key, long exp) throws DatabaseExecption {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setWExpire(String key, String value, int expire) throws DatabaseExecption {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean exists(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void incr(String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void incrBy(String key, int incr) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addMultiple(HashMap<String, String> hMap) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HashMap<String, String> getMultiple(List<String> get) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getType(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
