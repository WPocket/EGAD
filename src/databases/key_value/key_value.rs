use std::collections::HashMap;

use crate::structs::errors::KVError;
pub trait KeyValue{

    fn connect() -> Result<bool, KVError>;
    fn disconnect() -> Result<bool, KVError>;
    
    fn ping() -> Result<u64, KVError>;

    fn get(key:String) -> Result<String, KVError>;

    fn set_s(key:String, value:String) -> Result<bool, KVError>;
    fn set_u64(key:String, value:u64) -> Result<bool, KVError>;

    fn set_s_ex(key:String, value:String, expire:u64) -> Result<bool, KVError>;
    fn set_u64_ex(key:String, value:u64, expire:u64) -> Result<bool, KVError>;


    fn exists(key:String) ->  Result<bool,KVError>;

    fn incr(key:String) -> Result<bool, KVError>;
    fn incr_by(key:String, incr:u64) -> Result<bool, KVError>;

    fn delete(key:String) -> Result<bool, KVError>;

    fn add_mul_s(kv_set:HashMap<String,String>) -> Result<bool, KVError>;
    fn add_mul_u64(kv_set:HashMap<String,u64>) -> Result<bool, KVError>;

    fn get_mul(keys:Vec<String>) -> Result<HashMap<String,String>, KVError>;
    
    fn get_type(key:String) -> Result<String, KVError>;

    fn set_ex(key:String, expire:u64) -> Result<bool, KVError>;
}