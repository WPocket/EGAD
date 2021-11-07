package com.alexcomeau.database.relational.mysql;

import com.alexcomeau.database.DatabaseExecption;
import com.alexcomeau.database.relational.Relational;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql implements Relational{
    private Connection con;
    public MySql() throws DatabaseExecption{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){
            throw new DatabaseExecption("01", e.getMessage(), e);
        }
    }
}
