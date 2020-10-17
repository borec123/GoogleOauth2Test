package com.example.demo.controller;


import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.avast.business.authorization.core.model.IdentityDetails;
import com.example.demo.identity.GoogleIdentityProvider;

import io.swagger.v3.oas.annotations.Parameter;
import reactor.core.publisher.Mono;

@RestController
public class MyController {
	
	GoogleIdentityProvider identityProvider = new GoogleIdentityProvider();

    @GetMapping("/")
    public Publisher<String> home() {

        return Mono.just("Home page");
    }
    
	/*
	 * @GetMapping("/finish") public Publisher<String> finish() {
	 * 
	 * return Mono.just("Welcome to Internal system."); }
	 */    
    
    
    @GetMapping("/connect")
    public Publisher<HttpResponse<String>> connect() {

    	try { 
    		HttpResponse<String> resp = identityProvider.googleLogin();
    		
    		
    	
        
		return Mono.just(resp);
		
    	}
		
		catch(Exception ex) {
			ex.printStackTrace();
			return Mono.just(null);
		}
    }
    
    @GetMapping("/hello")
    public Publisher<String> hello(@RequestParam("state") String state, @Parameter(hidden = true) ServerHttpRequest request,
            @Parameter(hidden = true) ServerHttpResponse response) {

    	
    	MultiValueMap<String, String> map = request.getQueryParams(); 

		/*
		Set<String> keyset = map.keySet(); 
		keyset.forEach(k -> System.out.println(k + ":" + map.get(k)));
		*/   
    	
    	List<String> code = map.get("code");
    	
    	if(code.size() < 1)
    		throw new RuntimeException("Code not received !!!");
    	
    	//identityProvider.getGoogleTokens(code.get(0));
    	
    	Mono<String> mo = identityProvider.getGoogleTokensAsync(code.get(0));
    	
    	
		//--- Remove it :  !!!
    	mo.subscribe( m -> System.out.println(m));
   	
   	
        
		return Mono.just("Callback ! " + state);
    }

    
}