package com.alexcomeau.database.keyvalue.redis;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.keyvalue.KeyValue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.InvalidURIException;

public class Redis implements KeyValue{
    private Jedis jedis;

    public Redis(Database db){
        if(db == null){
            jedis = new Jedis();
            return;
        }
        try{
            jedis = new Jedis(URI.create(db.getUrl()));
        }catch(InvalidURIException e){
            jedis = new Jedis();
            return;
        }
    }

    @Override
    public void Connect() throws DatabaseException {
        if(!jedis.isConnected()){
            jedis.connect();
        }
    }

    @Override
    public void Disconnect() throws DatabaseException {
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
    public String get(String key) throws DatabaseException {
        String out;
        try{
            jedis.connect();
            out = jedis.get(key);
            if(out == null){
                throw new DatabaseException("20", "value does not exist");
            }
            if(out.equals("nil")){
                throw new DatabaseException("20", "value does not exist");
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        return out;
    }

    @Override
    public void set(String key, String value) throws DatabaseException {
        String reply;
        try{
            jedis.connect();
            reply = jedis.set(key, value);
            if(reply.equals("OK")){
                return;
            }if(reply.equals("nil")){
                throw new DatabaseException("10", "value was not set");
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
    }

    @Override
    public void setKeyExpire(String key, long exp) throws DatabaseException {
        try{
            jedis.connect();
            double rep = jedis.expire(key, exp);
            if(rep == 0){
                throw new DatabaseException("11", "key was not set");
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
    }

    @Override
    public void setWExpire(String key, String value, long expire) throws DatabaseException {
        try{
            jedis.connect();
            String reply = jedis.setex(key, expire, value);
            if(reply.equals("OK")){
                return;
            }if(reply.equals("nil")){
                throw new DatabaseException("10", "value was not set");
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1", e.getMessage(), e);
        }
        
    }

    @Override
    public boolean exists(String key) throws DatabaseException {
        try{
            jedis.connect();
            boolean rep = jedis.exists(key);
            return rep;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
    }

    @Override
    public long incr(String key) throws DatabaseException {
        try{
            jedis.connect();
            long rep = jedis.incr(key);
            return rep;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        
    }

    @Override
    public long incrBy(String key, long incr) throws DatabaseException {
        try{
            jedis.connect();
            long rep = jedis.incrBy(key, incr);
            return rep;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        
        
    }

    @Override
    public void delete(String key) throws DatabaseException  {
        try{
            jedis.connect();
            long rep = jedis.del(key);
            if(rep != 1){
                throw new DatabaseException("11", "key was not removed");
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        
    }

    @Override
    public void addMultiple(HashMap<String, String> hMap) throws DatabaseException {
        try{
            jedis.connect();
            String errorKeys = "";
            for(String key :  hMap.keySet()){
                try{
                    this.set(key, hMap.get(key));
                }catch(DatabaseException e){
                    errorKeys += key + " ";
                }
            }
            if(!errorKeys.equals("")){
                throw new DatabaseException("12", "was unable to set: " + errorKeys);
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        
        
    }

    @Override
    public HashMap<String, String> getMultiple(ArrayList<String> get) throws DatabaseException  {
        jedis.connect();
        HashMap<String, String> hMap = new HashMap<>();
        try{
            String errorKeys = "";
            for(String s : get){
                try{
                    String addme = this.get(s);
                    hMap.put(s, addme);
                }catch(DatabaseException e){
                    errorKeys += s + " ";
                }
            }
            if(hMap.size() != 0){
                return hMap;
            }
            if(!errorKeys.equals("")){
                throw new DatabaseException("12", "was unable to set: " + errorKeys);
            }
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public String getType(String key) throws DatabaseException {
        try{
            jedis.connect();
            String rep = jedis.type(key);
            if(rep == "none"){
                throw new DatabaseException("12", "key does not exist");
            }
            return rep;
        }catch(DatabaseException e){
            throw e;
        }catch(Exception e){
            throw new DatabaseException("1",e.getMessage(), e);
        }
    }
    
}
