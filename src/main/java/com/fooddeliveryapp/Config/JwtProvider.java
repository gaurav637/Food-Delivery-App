package com.fooddeliveryapp.Config;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	public static final long JWT_TOKEN_VALIDITY =  7 * 24 * 60 * 60 * 1000;// seven days 

	public String doGenerateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }
	
	
	public String doGenerateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
        		.setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }
	
	
	public Key getSignSecretKey() {
		byte[] key = Decoders.BASE64.decode("4DHF9090FI3NMN4M389DSJDK4738463874JHFJDSHFSDFMCVNXM9834792374923JFHJKDJFHKSD847398");
		return Keys.hmacShaKeyFor(key);
	}
	
////	public Key getSignSecretKey() {
////	    String base64EncodedKey = "NERIRjkwOTAxRkkzTk1OMU0zODlEU0pESzQ3Mzg0NjM4NzRKSEZKRFNI" +
////	                              "RlNERkNDVk5YTjk4MzQ3OTIzNzQ5MjNKRkhKS0RKRkhLU0Q4NDczOTg=";
////	    byte[] keyBytes = Base64.getDecoder().decode(base64EncodedKey);
////	    return new SecretKeySpec(keyBytes, "HmacSHA256");
////	}
////	
//	
//	
//	public SecretKey getSignSecretKey() {
//	    // Read secret key from a secure location (e.g., environment variable)
//	    String base64UrlEncodedKey = System.getenv("SECRET_KEY");
//
//	    byte[] decodedKeyBytes = base64UrlDecode(base64UrlEncodedKey);
//	    return new SecretKeySpec(decodedKeyBytes, "HmacSHA256");
//	}
//
//	private byte[] base64UrlDecode(String base64UrlEncodedKey) {
//	    // Explain the purpose of character replacement
//	    String base64EncodedKey = base64UrlEncodedKey
//	            .replace('-', '+') // Convert '-' to '+' for Base64 decoding
//	            .replace('_', '/'); // Convert '_' to '/' for Base64 decoding
//
//	    return Base64.getUrlDecoder().decode(base64EncodedKey);
//	}
	
	
	
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignSecretKey()).build().parseClaimsJws(token).getBody();
    }
	
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	

}
