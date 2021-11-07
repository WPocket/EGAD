package com.alexcomeau.database.relational;

import java.util.ArrayList;
import java.util.HashMap;

import com.alexcomeau.database.DatabaseExecption;

import org.apache.commons.lang3.tuple.Pair;

public interface Relational {
    public void connect() throws DatabaseExecption;
    public void disconnect()throws DatabaseExecption;

    public void insert(String table, HashMap<String, Pair<String, String>> values)throws DatabaseExecption;
    
    public ArrayList<String> select(String value, String table)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultiple(ArrayList<String> multiple, String table)throws DatabaseExecption;

    public ArrayList<String> selectWhere(String single, HashMap<String, String> where)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleWhere(ArrayList<String> multiple, String table, HashMap<String, String> where)throws DatabaseExecption;

    public ArrayList<String> selectOffset(String value, String table, int offset)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleOffset(ArrayList<String> multiple, String table, int offset)throws DatabaseExecption;

    public ArrayList<String> selectWhereOffset(String single, HashMap<String, String> where, int offset)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffset(ArrayList<String> multiple, String table, HashMap<String, String> where, int offset)throws DatabaseExecption;

    public ArrayList<String> selectMax(String value, String table, int max)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleMax(ArrayList<String> multiple, String table, int max)throws DatabaseExecption;

    public ArrayList<String> selectWhereMax(String single, HashMap<String, String> where, int max)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleWhereMax(ArrayList<String> multiple, String table, HashMap<String, String> where, int max)throws DatabaseExecption;

    public ArrayList<String> selectOffsetMax(String value, String table, int offset, int max)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleOffsetMax(ArrayList<String> multiple, String table, int offset, int max)throws DatabaseExecption;

    public ArrayList<String> selectWhereOffsetMax(String single, HashMap<String, String> where, int offset, int max)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffsetMax(ArrayList<String> multiple, String table, HashMap<String, String> where, int offset, int max)throws DatabaseExecption;

    
    public ArrayList<String> orderByMul(ArrayList<String> data, ArrayList<String> columns)throws DatabaseExecption;
    public ArrayList<String> orderBy(ArrayList<String> data, String col)throws DatabaseExecption;

    public ArrayList<HashMap<String, String>> orderMulByMul(ArrayList<HashMap<String, String>> data, ArrayList<String> columns)throws DatabaseExecption;
    public ArrayList<HashMap<String, String>> orderMulBy(ArrayList<HashMap<String, String>> data, String col)throws DatabaseExecption;

    public boolean newTable(String name, ArrayList<String> types, HashMap<String, String> options)throws DatabaseExecption;
    public boolean deleteTable(String name)throws DatabaseExecption;

    public boolean dropRow(HashMap<String, String> where, String table, int count)throws DatabaseExecption;

    public ArrayList<String> listColumns(String name)throws DatabaseExecption;

    public long getTableSize(String table)throws DatabaseExecption;

    public boolean dropColumn(String name, String table)throws DatabaseExecption;



}
