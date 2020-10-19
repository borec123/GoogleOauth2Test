package com.example.demo.controller;


import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.identity.GoogleIdentityProvider;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

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


		Set<String> keyset = map.keySet(); 
		keyset.forEach(k -> System.out.println(k + ":" + map.get(k)));
  
    	
    	List<String> code = map.get("code");
    	
    	if(code.size() < 1)
    		throw new RuntimeException("Code not received !!!");
    	
    	//identityProvider.getGoogleTokens(code.get(0));
    	
    	Mono<String> mo = identityProvider.getGoogleTokensAsync(code.get(0));
    	
    	
		//--- Remove it :  !!!
    	mo.subscribe( m -> System.out.println(m));
   	
   	
        
		return Mono.just("Callback ! " + state);
    }

    public static void main(String[] args) throws IllegalArgumentException, UnsupportedEncodingException, ParseException {
    	String s = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjdkYTc4NjNlODYzN2Q2NjliYzJhMTI2MjJjZWRlMmE4ODEzZDExYjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MzAwMTIwMDk4NDgtbjFzOGNvb24wcDJuNnE1ZWs4ZGlzcDlmYmUybjh1ZnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MzAwMTIwMDk4NDgtbjFzOGNvb24wcDJuNnE1ZWs4ZGlzcDlmYmUybjh1ZnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ2MTM4MTE0NTI4NDg1NzYwMDkiLCJoZCI6ImF2YXN0LmNvbSIsImVtYWlsIjoicGV0ci5oZWN6a29AYXZhc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJNUWp6anhvUExsQW5VTEY3S2pTN1pnIiwibm9uY2UiOiIwMzk0ODUyLTMxOTA0ODUtMjQ5MDM1OCIsImlhdCI6MTYwMjk1NDY0MiwiZXhwIjoxNjAyOTU4MjQyfQ.jBEhVmN6rLmDW6HLnz0eviukFbUQQDsEQeVvwQuAr29BN7d61otj1It0a446uo7-f7mFEbkrrKNJ2_C76vfoCGvxno4WMme2IZZfe4IqZlSLqdWdj6meqPxMtXdS_2tj1eY_lXmAmO2M20Mrki1GqjKKkePwORh2KX5EM3LII7H3zeKZtMz5BIdFIHV6ZTrBj4S-zqHfBYC0W1rJu02gGwet4aDnVov0WiVEP2y6gWI0cTUAOJk-2-QXP0f04EyRjGPECY0wezPRwC-HtIcKPdLvGkoSa3gcQEQE9w1tJoV6SnGSqlbCfVfu7HCwldTOJDcRzaHGyLXvB5DmKECzkA";
    	
    	//Claims claims = decodeJWT(s);
    	
    	//System.out.println(claims.toString());
    	System.out.println(parseJWT(null, s));
    	
    }
    
    private static String parseJWT(String claim, String accessToken) throws ParseException {
        SignedJWT decodedJWT;

            decodedJWT = SignedJWT.parse(accessToken);
            
            JWTClaimsSet s = decodedJWT.getJWTClaimsSet();
            
            String header = decodedJWT.getHeader().toString();
            
            System.out.println(header);
            System.out.println("-----------------------------------------");

            s.getClaims().keySet()
            .forEach(c -> System.out.println(c + " = " + s.getClaims().get(c)));
            
            
                       
            
            
			
        return s.toString();

    }

}