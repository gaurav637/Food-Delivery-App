package com.fooddeliveryapp.Config;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.Constants.jwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	private SecretKey key = Keys.hmacShaKeyFor(jwtConstants.SECRET_KEY.getBytes());
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority>authorities = auth.getAuthorities();
		String roles = populateAuthorities(authorities);
		
		String jwt = Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // converting to milliseconds
				.claim("email",auth.getName())
				.claim("authorities", roles)
				.signWith(SignatureAlgorithm.HS512, key)
                .compact();
                
        return jwt;         

	}

	public String getEmailFromToken(String token) {
		token = token.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    	String email = String.valueOf(claims.get("email"));
    	return email;
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auth = new HashSet<>();
		for(GrantedAuthority authority:authorities) {
			auth.add(authority.getAuthority());
		}
		return String.join(",",auth);
	}
}
