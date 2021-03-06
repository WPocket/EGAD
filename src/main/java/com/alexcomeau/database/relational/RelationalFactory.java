package com.alexcomeau.database.relational;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.database.relational.mysql.MySql;

public class RelationalFactory {
    public RelationalFactory() {
    }

    public Relational parseDB(Database db) throws DatabaseException{
        switch (db.getSupplier().toLowerCase()) {
            case "mysql":
                return new MySql(db);
            default:
                //do something
        }
        return null;
    }
}
