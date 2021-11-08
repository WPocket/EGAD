package com.alexcomeau.database.relational.mysql;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.relational.Relational;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MySql implements Relational{
    private Connection con;
    private Database db;
    public MySql(Database db) throws DatabaseExecption{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.db = db;
        }catch(Exception e){
            throw new DatabaseExecption("01", e.getMessage(), e);
        }
    }
    @Override
    public void connect() throws DatabaseExecption {
        try{
            if(con == null || con.isClosed()){
                con = DriverManager.getConnection("jdbc:" + db.getUrl());
            }
        }catch(Exception e){
            throw new DatabaseExecption("05", e.getMessage(), e);
        }
        
    }
    @Override
    public void disconnect() throws DatabaseExecption {
        try {
            if(con != null && !con.isClosed()){
                con.close();
            }
        } catch (SQLException e) {
            
            throw new DatabaseExecption("05", e.getMessage(), e);
        }
    
    }
    @Override
    public void insert(String table, HashMap<String, Pair<String, String>> values) throws DatabaseExecption {
        //flesh this out
        
    }
    @Override
    public ArrayList<String> select(String value, String table) throws DatabaseExecption {
        try{
            connect();
            PreparedStatement stmt = con.prepareStatement("SELECT " + value + " FROM " + table);
    

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while(rs.next()){
                String addme = rs.getString(1);
                if(addme == null){
                    addme = "NULL";
                }
                result.add(addme);
            }
            disconnect();
            return result;
        }catch(Exception e){
            disconnect();
            throw new DatabaseExecption("05", e.getMessage(), e);
        }
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultiple(ArrayList<String> multiple, String table)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectWhere(String single, HashMap<String, String> where) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhere(ArrayList<String> multiple, String table,
            HashMap<String, String> where) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectOffset(String value, String table, int offset) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleOffset(ArrayList<String> multiple, String table, int offset)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectWhereOffset(String single, HashMap<String, String> where, int offset)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffset(ArrayList<String> multiple, String table,
            HashMap<String, String> where, int offset) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectMax(String value, String table, int max) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleMax(ArrayList<String> multiple, String table, int max)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectWhereMax(String single, HashMap<String, String> where, int max)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhereMax(ArrayList<String> multiple, String table,
            HashMap<String, String> where, int max) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectOffsetMax(String value, String table, int offset, int max) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleOffsetMax(ArrayList<String> multiple, String table,
            int offset, int max) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> selectWhereOffsetMax(String single, HashMap<String, String> where, int offset, int max)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffsetMax(ArrayList<String> multiple, String table,
            HashMap<String, String> where, int offset, int max) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> orderByMul(ArrayList<String> data, ArrayList<String> columns) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<String> orderBy(ArrayList<String> data, String col) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> orderMulByMul(ArrayList<HashMap<String, String>> data,
            ArrayList<String> columns) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public ArrayList<HashMap<String, String>> orderMulBy(ArrayList<HashMap<String, String>> data, String col)
            throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public boolean newTable(String name, ArrayList<String> types, HashMap<String, String> options)
            throws DatabaseExecption {
        //flesh this out
        return false;
    }
    @Override
    public boolean deleteTable(String name) throws DatabaseExecption {
        //flesh this out
        return false;
    }
    @Override
    public boolean dropRow(HashMap<String, String> where, String table, int count) throws DatabaseExecption {
        //flesh this out
        return false;
    }
    @Override
    public ArrayList<String> listColumns(String name) throws DatabaseExecption {
        //flesh this out
        return null;
    }
    @Override
    public long getTableSize(String table) throws DatabaseExecption {
        //flesh this out
        return 0;
    }
    @Override
    public boolean dropColumn(String name, String table) throws DatabaseExecption {
        //flesh this out
        return false;
    }
}
