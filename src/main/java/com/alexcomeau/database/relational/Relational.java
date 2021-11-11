package com.alexcomeau.database.relational;

import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.database.DatabaseException;

import org.apache.commons.lang3.tuple.Pair;

public interface Relational {
    public void connect() throws DatabaseException;
    public void disconnect()throws DatabaseException;

    public void insert(String table, ArrayList<Pair<String, String>> values)throws DatabaseException;
    
    public String select(String value, String table)throws DatabaseException;
    public HashMap<String, String> selectMultiple(ArrayList<String> multiple, String table)throws DatabaseException;

    public String selectWhere(String table, String valueString,String where)throws DatabaseException;
    public HashMap<String, String> selectMultipleWhere(ArrayList<String> multiple, String table,String where)throws DatabaseException;

    public String selectOffset(String value, String table, int offset)throws DatabaseException;
    public HashMap<String, String> selectMultipleOffset(ArrayList<String> multiple, String table, int offset)throws DatabaseException;

    public String selectWhereOffset(String table, String valueString,String where, int offset)throws DatabaseException;
    public HashMap<String, String> selectMultipleWhereOffset(ArrayList<String> multiple, String table,String where, int offset)throws DatabaseException;

    public ArrayList<String> selectMax(String value, String table, int max)throws DatabaseException;
    public ArrayList<HashMap<String, String>> selectMultipleMax(ArrayList<String> multiple, String table, int max)throws DatabaseException;

    public ArrayList<String> selectWhereMax(String value, String table, String where, int max)throws DatabaseException;
    public ArrayList<HashMap<String, String>> selectMultipleWhereMax(ArrayList<String> multiple, String table,String where, int max)throws DatabaseException;

    public ArrayList<String> selectOffsetMax(String value, String table, int offset, int max)throws DatabaseException;
    public ArrayList<HashMap<String, String>> selectMultipleOffsetMax(ArrayList<String> multiple, String table, int offset, int max)throws DatabaseException;

    public ArrayList<String> selectWhereOffsetMax(String value, String table,String where, int offset, int max)throws DatabaseException;
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffsetMax(ArrayList<String> multiple, String table,String where, int offset, int max)throws DatabaseException;

    
    public ArrayList<String> orderByMul(ArrayList<String> data, ArrayList<String> columns)throws DatabaseException;
    public ArrayList<String> orderBy(ArrayList<String> data, String col)throws DatabaseException;

    public ArrayList<HashMap<String, String>> orderMulByMul(ArrayList<HashMap<String, String>> data, ArrayList<String> columns)throws DatabaseException;
    public ArrayList<HashMap<String, String>> orderMulBy(ArrayList<HashMap<String, String>> data, String col)throws DatabaseException;

    public boolean newTable(String name, ArrayList<String> types, HashMap<String, String> options)throws DatabaseException;
    public boolean deleteTable(String name)throws DatabaseException;

    public boolean dropRow(HashMap<String, String> where, String table, int count)throws DatabaseException;

    public ArrayList<String> listColumns(String name)throws DatabaseException;

    public long getTableSize(String table)throws DatabaseException;

    public boolean dropColumn(String name, String table)throws DatabaseException;



}
