use std::collections::HashMap;

use crate::structs::errors::KVError;
pub trait KeyValue {
    fn connect(self) -> Result<bool, KVError>;
    fn disconnect(self) -> Result<bool, KVError>;

    fn ping(self) -> Result<u64, KVError>;

    fn get(self, key: String) -> Result<String, KVError>;

    fn set_s(self, key: String, value: String) -> Result<bool, KVError>;
    fn set_u64(self, key: String, value: u64) -> Result<bool, KVError>;

    fn set_s_ex(self, key: String, value: String, expire: u64) -> Result<bool, KVError>;
    fn set_u64_ex(self, key: String, value: u64, expire: u64) -> Result<bool, KVError>;

    fn exists(self, key: String) -> Result<bool, KVError>;

    fn incr(self, key: String) -> Result<bool, KVError>;
    fn incr_by(self, key: String, incr: u64) -> Result<bool, KVError>;

    fn delete(self, key: String) -> Result<bool, KVError>;

    fn add_mul_s(self, kv_set: HashMap<String, String>) -> Result<bool, KVError>;
    fn add_mul_u64(self, kv_set: HashMap<String, u64>) -> Result<bool, KVError>;

    fn get_mul(self, keys: Vec<String>) -> Result<HashMap<String, String>, KVError>;

    fn get_type(self, key: String) -> Result<String, KVError>;

    fn set_ex(self, key: String, expire: u64) -> Result<bool, KVError>;
}
