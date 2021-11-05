#[derive(Debug)]
pub enum KVError {
    TimeoutError = 0,
    ConnectError = 1
}
pub enum RelError {
    TimeoutError = 0,
    ConnectError = 1
}