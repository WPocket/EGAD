use std::collections::HashMap;

use crate::structs::errors::KVError;

use super::key_value::KeyValue;

pub struct Redis{
    db_url:String,
    db_ip:String,
    db_user:String,
    db_passwd:String,
}

impl KeyValue for Redis{
    fn connect() -> Result<bool, KVError>{Ok(true)}
    fn disconnect() -> Result<bool, KVError>{Ok(true)}
    
    fn ping() -> Result<u64, KVError>{Ok(0)}

    fn get(key:String) -> Result<String, KVError>{Ok("ok".to_owned())}

    fn set_s(key:String, value:String) -> Result<bool, KVError>{Ok(true)}
    fn set_u64(key:String, value:u64) -> Result<bool, KVError>{Ok(true)}

    fn set_s_ex(key:String, value:String, expire:u64) -> Result<bool, KVError>{Ok(true)}
    fn set_u64_ex(key:String, value:u64, expire:u64) -> Result<bool, KVError>{Ok(true)}


    fn exists(key:String) ->  Result<bool,KVError>{Ok(true)}

    fn incr(key:String) -> Result<bool, KVError>{Ok(true)}
    fn incr_by(key:String, incr:u64) -> Result<bool, KVError>{Ok(true)}

    fn delete(key:String) -> Result<bool, KVError>{Ok(true)}

    fn add_mul_s(kv_set:HashMap<String,String>) -> Result<bool, KVError>{Ok(true)}
    fn add_mul_u64(kv_set:HashMap<String,u64>) -> Result<bool, KVError>{Ok(true)}

    fn get_mul(keys:Vec<String>) -> Result<HashMap<String,String>, KVError>{Err(KVError::ConnectError)}
    
    fn get_type(key:String) -> Result<String, KVError>{Ok("ok".to_owned())}

    fn set_ex(key:String, expire:u64) -> Result<bool, KVError>{Ok(true)}
}