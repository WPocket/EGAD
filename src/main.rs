
use actix_web::{ App, HttpServer, web, HttpResponse, Responder};
use serde::{Serialize, Deserialize};
use actix_cors::Cors;

extern crate simple_logger;
use simple_logger::{SimpleLogger};
use log::LevelFilter;


// Configure route
pub fn general_routes(cfg: &mut web::ServiceConfig) {
    //general purpose
    cfg.route("/health", web::get().to(health_check_handler));
}

pub async fn health_check_handler() ->  impl Responder {
    
    log::info!("returning the health of the api server");
    HttpResponse::Ok().json("Hello World")
}

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    
    

    SimpleLogger::new()
    .with_level(LevelFilter::Error)
    .with_module_level("basic_forum_api", LevelFilter::Info)
    .with_module_level("actix", LevelFilter::Info)
    .init()
    .unwrap();
    
    HttpServer::new(|| {
        let cors = Cors::new()
        .supports_credentials()
            .supports_credentials()
            .max_age(3600).finish();

        App::new()
            .wrap(cors)
            .configure(general_routes)
            
    })
        .workers(20)
        .keep_alive(15)
        .bind("0.0.0.0:8080")?
        .run()
        .await
}