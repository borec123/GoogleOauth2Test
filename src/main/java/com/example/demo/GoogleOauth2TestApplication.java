package com.example.demo;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleOauth2TestApplication {

	
	  public static void main(String[] args) {
	  
	  
	  SpringApplication.run(GoogleOauth2TestApplication.class, args);
	  
	  
	  
		/*
		 * // Create a state token to prevent request forgery. // Store it in the
		 * session for later validation. String state = new BigInteger(130, new
		 * SecureRandom()).toString(32); request.session().attribute("state", state); //
		 * Read index.html into memory, and set the client ID, // token state, and
		 * application name in the HTML before serving it. return new Scanner(new
		 * File("index.html"), "UTF-8") .useDelimiter("\\A").next()
		 * .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
		 * .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
		 * .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}", APPLICATION_NAME);
		 */	  
	  
	  
	  
	  }
	 
	
	
	
}
