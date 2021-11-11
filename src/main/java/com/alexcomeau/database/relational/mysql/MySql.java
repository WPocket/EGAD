package com.alexcomeau.database.relational.mysql;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.relational.Relational;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MySql implements Relational {
    private Connection con;
    private Database db;

    public MySql(Database db) throws DatabaseException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.db = db;
        } catch (Exception e) {
            throw new DatabaseException("01", e.getMessage(), e);
        }
    }

    @Override
    public void connect() throws DatabaseException {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection("jdbc:" + db.getUrl());
            }
        } catch (Exception e) {
            throw new DatabaseException("05", e.getMessage(), e);
        }

    }

    @Override
    public void disconnect() throws DatabaseException {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {

            throw new DatabaseException("05", e.getMessage(), e);
        }

    }

    @Override
    public void insert(String table, ArrayList<Pair<String, String>> values) throws DatabaseException {
        try {
            connect();
            String sql = "INSERT INTO" + table + "(";
            String sqlEnd = " VALUES (";
            for (Pair<String, String> p : values) {
                sql += "\"" + p.getLeft() + "\",";
                sqlEnd += "\"" + p.getRight() + "\",";
            }
            sql = (String) sql.substring(0, sql.length() - 1) + ")";
            sqlEnd = (String) sqlEnd.substring(0, sqlEnd.length() - 1) + ")";
            PreparedStatement stmt = con.prepareStatement(sql + sqlEnd);

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (rs.next()) {
                String addme = rs.getString(1);
                if (addme == null) {
                    addme = "NULL";
                }
                result.add(addme);
            }
            disconnect();
        } catch (Exception e) {
            disconnect();
            throw new DatabaseException("05", e.getMessage(), e);
        }

    }

    @Override
    public String select(String value, String table) throws DatabaseException {
        return selectWhereOffset(table, value, "", 0);
    }

    @Override
    public HashMap<String, String> selectMultiple(ArrayList<String> multiple, String table) throws DatabaseException {
        return selectMultipleWhereOffset(multiple, table, "", 0);
    }

    @Override
    public String selectWhere(String table, String valueString, String where) throws DatabaseException {
        return selectWhereOffset(table, valueString, where, 0);
    }

    @Override
    public HashMap<String, String> selectMultipleWhere(ArrayList<String> multiple, String table, String where)
            throws DatabaseException {
        return selectMultipleWhereOffset(multiple, table, where, 0);
    }

    @Override
    public String selectOffset(String value, String table, int offset) throws DatabaseException {
        return selectWhereOffset(table, value, "", offset);
    }

    @Override
    public HashMap<String, String> selectMultipleOffset(ArrayList<String> multiple, String table, int offset)
            throws DatabaseException {
        return selectMultipleWhereOffset(multiple, table, "", offset);
    }

    @Override
    public String selectWhereOffset(String table, String value, String where, int offset) throws DatabaseException {
        String offsetStr = " LIMIT " + ((offset != 0) ? offset + ",1" : "0,1");
        String whereStr = ((!where.equals("")) ? " WHERE " + where : "");
        try {
            if(value.equals("")){
                throw new DatabaseException("33", "the value cannot be empty");
            }
            if(table.equals("")){
                throw new DatabaseException("34", "the table name cannot be empty");
            }
            connect();
            PreparedStatement stmt = con.prepareStatement("SELECT " + value + " FROM " + table + whereStr + offsetStr);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String addme = rs.getString(1);
                disconnect();
                return addme;
            }
            throw new DatabaseException("30", "value was not found/was empty");
        }catch(DatabaseException e){
            throw e;
        } catch (Exception e) {
            disconnect();
            throw new DatabaseException("05", e.getMessage(), e);
        }
    }

    @Override
    public HashMap<String, String> selectMultipleWhereOffset(ArrayList<String> multiple, String table, String where,
            int offset) throws DatabaseException {
            if(multiple.size() == 0){
                throw new DatabaseException("33", "the list of values cannot be empty");
            }
            if(table.equals("")){
                throw new DatabaseException("34", "the table name cannot be empty");
            }
            
            HashMap<String, String> output = new HashMap<>();
            String offsetStr = " LIMIT " + ((offset != 0) ? offset + ",1" : "0,1");
            String whereStr = ((!where.equals("")) ? " WHERE " + where : "");
            String values = "";
            for(String s : multiple){
                values += s + ",";
            }
            values = values.substring(0, values.length() - 1);
            try {
                connect();
                PreparedStatement stmt = con.prepareStatement("SELECT " + values + " FROM " + table + whereStr + offsetStr);
    
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    for(int i = 0; i < multiple.size(); i++){
                        output.put(multiple.get(i), rs.getString(multiple.get(i)));
                    }
                }
                return output;
            } catch(DatabaseException e){
                throw e;
            }catch (Exception e) {
                disconnect();
                throw new DatabaseException("05", e.getMessage(), e);
            }
    }

    @Override
    public ArrayList<String> selectMax(String value, String table, int max) throws DatabaseException {
        return selectWhereOffsetMax(value, table, "", 0, max);
    }

    @Override
    public ArrayList<HashMap<String, String>> selectMultipleMax(ArrayList<String> multiple, String table, int max)
            throws DatabaseException {
            return selectMultipleWhereOffsetMax(multiple, table, "", 0, max);
    }

    @Override
    public ArrayList<String> selectWhereMax(String value, String table, String where, int max) throws DatabaseException {
        return selectWhereOffsetMax(value, table, where, 0, max);
    }

    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhereMax(ArrayList<String> multiple, String table,
            String where, int max) throws DatabaseException {
        return selectMultipleWhereOffsetMax(multiple, table, where, 0, max);
    }

    @Override
    public ArrayList<String> selectOffsetMax(String value, String table, int offset, int max) throws DatabaseException {
        // flesh this out
        return selectWhereOffsetMax(value, table, "", offset, max);
    }

    @Override
    public ArrayList<HashMap<String, String>> selectMultipleOffsetMax(ArrayList<String> multiple, String table,
            int offset, int max) throws DatabaseException {
        return selectMultipleWhereOffsetMax(multiple, table, "", offset, max);
    }

    @Override
    public ArrayList<String> selectWhereOffsetMax(String value, String table, String where, int offset, int max)
            throws DatabaseException {
            String offsetStr = " LIMIT " + ((offset != 0) ? offset + "," + max : "0," + max);
            String whereStr = ((!where.equals("")) ? " WHERE " + where : "");
            try {
                if(value.equals("")){
                    throw new DatabaseException("33", "the value cannot be empty");
                }
                if(table.equals("")){
                    throw new DatabaseException("34", "the table name cannot be empty");
                }
                connect();
                PreparedStatement stmt = con.prepareStatement("SELECT " + value + " FROM " + table + whereStr + offsetStr);
    
                ResultSet rs = stmt.executeQuery();
                ArrayList<String> output = new ArrayList<>();
                while (rs.next()) {
                    String addme = rs.getString(1);
                    output.add(addme);
                }
                if(output.size() != 0){
                    return output;
                }
                throw new DatabaseException("30", "value was not found/was empty");
            }catch(DatabaseException e){
                throw e;
            } catch (Exception e) {
                disconnect();
                throw new DatabaseException("05", e.getMessage(), e);
            }
    }

    @Override
    public ArrayList<HashMap<String, String>> selectMultipleWhereOffsetMax(ArrayList<String> multiple, String table,
            String where, int offset, int max) throws DatabaseException {
        // flesh this out
        if(multiple.size() == 0){
            throw new DatabaseException("33", "the list of values cannot be empty");
        }
        if(table.equals("")){
            throw new DatabaseException("34", "the table name cannot be empty");
        }
        
        ArrayList<HashMap<String, String>> output = new ArrayList<>();
        String offsetStr = " LIMIT " + ((offset != 0) ? offset + "," + max : "0," + max);
        String whereStr = ((!where.equals("")) ? " WHERE " + where : "");
        String values = "";
        for(String s : multiple){
            values += s + ",";
        }
        values = values.substring(0, values.length() - 1);
        try {
            connect();
            PreparedStatement stmt = con.prepareStatement("SELECT " + values + " FROM " + table + whereStr + offsetStr);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HashMap<String, String> item = new HashMap<>();
                for(int i = 0; i < multiple.size(); i++){
                    item.put(multiple.get(i), rs.getString(multiple.get(i)));
                }
                output.add(item);
            }
            return output;
        } catch(DatabaseException e){
            throw e;
        }catch (Exception e) {
            disconnect();
            throw new DatabaseException("05", e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<String> orderByMul(ArrayList<String> data, ArrayList<String> columns) throws DatabaseException {
        // flesh this out
        return null;
    }

    @Override
    public ArrayList<String> orderBy(ArrayList<String> data, String col) throws DatabaseException {
        // flesh this out
        return null;
    }

    @Override
    public ArrayList<HashMap<String, String>> orderMulByMul(ArrayList<HashMap<String, String>> data,
            ArrayList<String> columns) throws DatabaseException {
        // flesh this out
        return null;
    }

    @Override
    public ArrayList<HashMap<String, String>> orderMulBy(ArrayList<HashMap<String, String>> data, String col)
            throws DatabaseException {
        // flesh this out
        return null;
    }

    @Override
    public boolean newTable(String name, ArrayList<String> types, HashMap<String, String> options)
            throws DatabaseException {
        // flesh this out
        return false;
    }

    @Override
    public boolean deleteTable(String name) throws DatabaseException {
        // flesh this out
        return false;
    }

    @Override
    public boolean dropRow(HashMap<String, String> where, String table, int count) throws DatabaseException {
        // flesh this out
        return false;
    }

    @Override
    public ArrayList<String> listColumns(String name) throws DatabaseException {
        // flesh this out
        return null;
    }

    @Override
    public long getTableSize(String table) throws DatabaseException {
        // flesh this out
        return 0;
    }

    @Override
    public boolean dropColumn(String name, String table) throws DatabaseException {
        // flesh this out
        return false;
    }
}
