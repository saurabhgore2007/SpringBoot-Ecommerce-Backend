package com.ecommerce.security;

import java.security.Key;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
  
	@Value("${jwt.secret}") 
	private String secret;
	
    @Value("${jwt.issuer}") 
    private String issuer;
    
    @Value("${jwt.expiry-minutes}") 
    private long expiryMinutes;

    private Key key ; //Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostConstruct
    public void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);;
    }

  public String generateToken(String email, String role) {
	  
    Instant now = Instant.now();
    
    return Jwts.builder().setSubject(email).claim("role", role).setIssuer(issuer)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plus(Duration.ofMinutes(expiryMinutes))))
      .signWith(key, SignatureAlgorithm.HS256).compact();
  }

  public Jws<Claims> parse(String token) 
    {
	    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}
}
