use crate::structs::errors::RelError;

pub trait Relational {
    fn connect() -> Result<bool, RelError>;
    fn disconnect() -> Result<bool, RelError>;
    
    fn ping() -> Result<u64, RelError>;
    
}