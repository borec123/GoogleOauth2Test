package com.example.demo.controller;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.googleapis.OAuth2Sample;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.Oauth2.Userinfo;

import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

  public Mono<ServerResponse> hello(ServerRequest request) {
	  
      Credential credential = null;
      
	    try {
	    	
	    	
	        credential = OAuth2Sample.login();
	  	// run commands
	        OAuth2Sample.tokenInfo(credential.getAccessToken());
	        
	        String username = OAuth2Sample.userInfo();

	        // success!
	        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
	        	      .body(BodyInserters.fromValue("Hello, this is an internal application !\n\n Logged in user: " + username));

	    } catch (IOException e) {
	        System.err.println(e.getMessage());
	      } catch (Throwable t) {
	        t.printStackTrace();
	      }

    return ServerResponse.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN)
      .body(BodyInserters.fromValue("Not authorized !"));
  }
}