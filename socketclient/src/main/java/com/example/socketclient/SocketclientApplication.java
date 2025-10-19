package com.example.socketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocketclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketclientApplication.class, args);
	}

}
