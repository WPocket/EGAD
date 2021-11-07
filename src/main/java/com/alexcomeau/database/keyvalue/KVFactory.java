package com.alexcomeau.database.keyvalue;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.keyvalue.redis.Redis;

public class KVFactory {
    public KVFactory() {
    }

    public KeyValue parseDB(Database db) {
        switch (db.getSupplier().toLowerCase()) {
            case "redis":
                return new Redis(db);
            default:
                //do something
        }
        return null;
    }
}
