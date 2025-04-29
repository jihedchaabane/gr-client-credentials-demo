package com.chj.gr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigServerApplication.class, args);
	}
	
	/**
	     http://localhost:8888/y-springboot-bar-service/default
	     http://localhost:8888/y-springboot-bar-service/dev
    	 http://localhost:8888/y-springboot-bar-service/prod
	 */
	/**
    	https://localhost:8443/y-springboot-bar-service/default
    	https://localhost:8443/y-springboot-bar-service/dev
	 	https://localhost:8443/y-springboot-bar-service/prod
	 */
	
	
}
