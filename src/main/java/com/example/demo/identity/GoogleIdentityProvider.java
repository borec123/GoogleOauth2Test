package com.example.demo.identity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Authenticator;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Duration;

import javax.net.ssl.SSLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.avast.business.authorization.core.model.IdentityDetails;

import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public class GoogleIdentityProvider implements com.avast.business.authorization.core.identity.IdentityProvider {

    //private final WebClient identityWebClient;
    
    private final java.net.http.HttpClient httpClient;
    
    private HttpClient nettyHttpClient;
    
    //private final WebClient revokeWebClient;

	public GoogleIdentityProvider(/* IdentityProviderSettings.AvastIdIdentityProviderSettings
									 * identityProviderSettings*/)
           {
    	
		
		httpClient = java.net.http.HttpClient.newBuilder()
		            .version(java.net.http.HttpClient.Version.HTTP_1_1)
		            .connectTimeout(Duration.ofSeconds(10))

		            .followRedirects(java.net.http.HttpClient.Redirect.ALWAYS)
		            .build();
		 
    	nettyHttpClient = HttpClient.create()
                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(3))
                                .addHandlerLast(new WriteTimeoutHandler(3))));
		/*
        if (isConfigured()) {
        	HttpClient identityHttpClient = HttpClient.create()
                    .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(3))
                                    .addHandlerLast(new WriteTimeoutHandler(3))));
            this.identityWebClient = WebClient.builder().baseUrl("https://accounts.google.com/o/oauth2/v2/auth")
                    .clientConnector(new ReactorClientHttpConnector(identityHttpClient)).build();

        } else {
            identityWebClient = null;

        }
        */
    }
    
    
    @Override
	public URI generateLink(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	public HttpResponse<String>  googleLogin() {
		HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=930012009848-n1s8coon0p2n6q5ek8disp9fbe2n8ufu.apps.googleusercontent.com&scope=openid%20email&redirect_uri=http%3A//localhost:12345/hello&state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome&login_hint=petr.heczko@avast.com&nonce=0394852-3190485-2490358&hd=avast.com"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();
	    
		HttpResponse<String>  responsebody = null;
		
		try {
			responsebody = processResponse(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
   
	return responsebody;
	}

	@Override
	public Mono<IdentityDetails> getIdentityDetails(String identifier) {
		throw new UnsupportedOperationException("Not supported.");
        
		
		/*
		  identityWebClient .get() 
				  .uri("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=930012009848-n1s8coon0p2n6q5ek8disp9fbe2n8ufu.apps.googleusercontent.com&scope=openid%20email&redirect_uri=http%3A//localhost:12345/hello&state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome&login_hint=petr.heczko@avast.com&nonce=0394852-3190485-2490358&hd=avast.com")
				  //.contentType(MediaType.APPLICATION_OCTET_STREAM)
		  //.bodyValue(null) //protobufObject.toByteString().toByteArray()) 
		  .exchange();
		  */
		  
			/*
			 * .flatMap(idResponse -> idResponse.bodyToMono(IdentityDetails.class))
			 * .map(this::getIdAvastServerResponseFromByteString) .doOnSuccess(details ->
			 * log.info("Identity details obtained, identifier:{}, identity_id:{}",
			 * identifier, details.getId()));
			 */
		  
		  
	}
	
	private HttpResponse<String> processResponse(HttpRequest request) throws InterruptedException, IOException {
		long start = System.nanoTime();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		long end = System.nanoTime();
		System.out.println("Time: " + (end - start) / 1000000.0 + " ms");

		// print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        int responseCode = response.statusCode();
		// print status code
        System.out.println("Response Code : " + responseCode );

        // print response body
        System.out.println(responseCode);			
		

		return response;
	}

	@Override
	public Mono<IdentityDetails> getIdentityDetailsByEmail(String email) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Mono<IdentityDetails> createIdentity(String email, String firstName, String lastName, String locale,
			String password) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Mono<HttpStatus> revoke(String identifier) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean isConfigured() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public String getLogoutUrl() {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	private String generateState() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}


	/**
	 * Uses a blocking synchronous version of Http client.
	 * Use {@link #getGoogleTokensAsync(String)} instead.
	 * @param code
	 */
	@Deprecated
	public void getGoogleTokens(String code) {
		
		String body = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) + "&"
				+ "client_id=930012009848-n1s8coon0p2n6q5ek8disp9fbe2n8ufu.apps.googleusercontent.com&"
				+ "client_secret=MR9acFeCYFAfX3Fgyk7z6aTF&"
				+ "redirect_uri=http%3A//localhost:12345/hello&"
				+ "grant_type=authorization_code";
		System.out.println("----------- Sending body: --------------");
		System.out.println(body);
		
		HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("https://oauth2.googleapis.com/token"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();
		
		System.out.println(request.toString());
	    
		HttpResponse<String>  responsebody = null;
		
		try {
			responsebody = processResponse(request);
			System.out.println("----------- body --------------");
			System.out.println(responsebody.body());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

    
    public Mono<String> getGoogleTokensAsync(String code) {
    	
		String body = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) + "&"
				+ "client_id=930012009848-n1s8coon0p2n6q5ek8disp9fbe2n8ufu.apps.googleusercontent.com&"
				+ "client_secret=MR9acFeCYFAfX3Fgyk7z6aTF&"
				+ "redirect_uri=http%3A//localhost:12345/hello&"
				+ "grant_type=authorization_code";
		
		Mono<String> mo = nettyHttpClient
        		.wiretap(true)
        		.headers(h -> h.add(HttpHeaderNames.CONTENT_TYPE, "application/x-www-form-urlencoded"))
        		.post()
                .uri(URI.create("https://oauth2.googleapis.com/token"))
                .send(ByteBufFlux.fromString(Mono.just(body)))
                .responseSingle(
                        (response, bytes) ->
                                bytes.asString()
                                        /*.map(it -> new Foo(response.status().code(), it))*/
                );
		
		return mo;
    }
    
	public HttpResponse<String>  obtainAdditionalUserInfo(String accessToken) {
		HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://openidconnect.googleapis.com/v1/userinfo?scope=https://www.googleapis.com/auth/userinfo.profile%20https://www.googleapis.com/auth/userinfo.email%20openid"))
                //.authenticator(null)
                
                .setHeader("Bearer",  accessToken)
                //.header("Bearer" , accessToken)
                //.uri(URI.create("https://www.googleapis.com/oauth2/v3/userinfo?scope=openid%20email"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
				//.setHeader("access_token", accessToken)
				//.setHeader("client_id", "930012009848-n1s8coon0p2n6q5ek8disp9fbe2n8ufu.apps.googleusercontent.com")
				//.setHeader("client_secret", "MR9acFeCYFAfX3Fgyk7z6aTF")
				//.setHeader("id_token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3OGFiMWRjNTkxM2Q5MjlkMzdjMjNkY2FhOTYxODcyZjhkNzBiNjgiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MzAwMTIwMDk4NDgtbjFzOGNvb24wcDJuNnE1ZWs4ZGlzcDlmYmUybjh1ZnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MzAwMTIwMDk4NDgtbjFzOGNvb24wcDJuNnE1ZWs4ZGlzcDlmYmUybjh1ZnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ2MTM4MTE0NTI4NDg1NzYwMDkiLCJoZCI6ImF2YXN0LmNvbSIsImVtYWlsIjoicGV0ci5oZWN6a29AYXZhc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJ1M3NoTGxpMUlBWjVLSVYyMGlyUUpRIiwibm9uY2UiOiIwMzk0ODUyLTMxOTA0ODUtMjQ5MDM1OCIsImlhdCI6MTYwMzExNDMzMiwiZXhwIjoxNjAzMTE3OTMyfQ.BMw_e1RYMAhIGEvaPp2G1_IxPnWpfFK_aK2UD0WuXk0TQOpNYtDUfYh3i5a3kOvkIqIDf_Dz3SGSFQpQrcfVu6AonNN2ulxRrVNRzd2uaM5vxsRklfe6593ixthOY0WwuQyz_vi4bMhYwFgmvRsWuiAa3a8QP_gYC65S5HAA-s4cox3ztgsIGWsR_6vOIrHxNB8HBG2Z5Fzr-OSR_vXItbdNUoCyDZ3ubDiUugr878e-L8oypbM93_RLipKVnFWlzVP2fluWdT4Y3x4r9t7SIetJWbFw8cO_CzOc3KqykQuKDfXIXJg5alaPh-KImfIkG0HovWNyFeLsKh61Maxe-A")
                .build();
		
	    // request.header(); //   ..setAuthorization("Bearer" + accessToken);		


	    
		HttpResponse<String>  responsebody = null;
		
		try {
			responsebody = processResponse(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsebody; 
	}
	
    public static void main(String[] args) throws IllegalArgumentException, UnsupportedEncodingException, ParseException {
    	String s = "ya29.a0AfH6SMBo3UNuSDLo6r6K3cDbEBCUaRaekQ-rU2SXwM9Dc7lXfIQsC45YubGRkfm_1hBVG1GrF37_OFqDnCui5zOfRnMNXiuw-1B8sqHtuX-mEZ_c7Tizakb7lcOYTre2nxRlb8Jn3VkGlHbaCk1tcDNUmfRGqbDYkfuK7h4tRWo";
    	
    	//Claims claims = decodeJWT(s);
    	
    	//System.out.println(claims.toString());
    	System.out.println(new GoogleIdentityProvider().obtainAdditionalUserInfo( s).body());
    	
    }

	
}
