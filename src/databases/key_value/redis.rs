use std::collections::HashMap;

use crate::structs::errors::KVError;

use super::key_value::KeyValue;

extern crate redis;

use redis::RedisResult;

pub struct Redis {
    db_url: String,
    db_ip: String,
    db_user: String,
    db_passwd: String,
}

impl KeyValue for Redis {
    fn ping(self) -> Result<u64, KVError> {
        Ok(0)
    }

    fn get(self, key: String) -> Result<String, KVError> {
        let client = redis::Client::open(self.db_url).unwrap();
        let mut con = client.get_connection();
        if (con.is_err()) {
            return Err(KVError::ConnectError);
        }
        let connect = con.unwrap();
        let result: RedisResult<String> = redis::cmd("GET").arg(key).query(&connect);
        Ok("ok".to_owned())
    }

    fn set_s(self, key: String, value: String) -> Result<bool, KVError> {
        Ok(true)
    }
    fn set_u64(self, key: String, value: u64) -> Result<bool, KVError> {
        Ok(true)
    }

    fn set_s_ex(self, key: String, value: String, expire: u64) -> Result<bool, KVError> {
        Ok(true)
    }
    fn set_u64_ex(self, key: String, value: u64, expire: u64) -> Result<bool, KVError> {
        Ok(true)
    }

    fn exists(self, key: String) -> Result<bool, KVError> {
        Ok(true)
    }

    fn incr(self, key: String) -> Result<bool, KVError> {
        Ok(true)
    }
    fn incr_by(self, key: String, incr: u64) -> Result<bool, KVError> {
        Ok(true)
    }

    fn delete(self, key: String) -> Result<bool, KVError> {
        Ok(true)
    }

    fn add_mul_s(self, kv_set: HashMap<String, String>) -> Result<bool, KVError> {
        Ok(true)
    }
    fn add_mul_u64(self, kv_set: HashMap<String, u64>) -> Result<bool, KVError> {
        Ok(true)
    }

    fn get_mul(self, keys: Vec<String>) -> Result<HashMap<String, String>, KVError> {
        Err(KVError::ConnectError)
    }

    fn get_type(self, key: String) -> Result<String, KVError> {
        Ok("ok".to_owned())
    }

    fn set_ex(self, key: String, expire: u64) -> Result<bool, KVError> {
        Ok(true)
    }
}

impl Redis {
    fn new(url: String, ip: String, user: String, passwd: String) -> Redis {
        return Redis {
            db_url: url,
            db_ip: ip,
            db_user: user,
            db_passwd: passwd,
        };
    }
}
