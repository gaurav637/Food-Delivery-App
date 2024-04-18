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
	
	//private SecretKey key = Keys.hmacShaKeyFor(jwtConstants.SECRET_KEY.getBytes());
	//private String key = "shdsdsireoiroemcxnGVHGHJJHUIcmnxmksdfjksdjfskdjdjFHFGJHGJJfjsdhfkjsdhfireowruBNBBVBCVFDFSFoiwerunmsadaksdjasdaskkjdkasjdaksd"
	
	
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

	public String doGenerateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // converting to milliseconds
                .signWith(getSignSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }
	
	
	public String doGenerateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
        		.setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // converting to milliseconds
                .signWith(getSignSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }
	
	
	public Key getSignSecretKey() {
		byte[] key = Decoders.BASE64.decode("4DHF9090FI3NMN4M389DSJDK4738463874JHFJDSHFSDFMCVNXM9834792374923JFHJKDJFHKSD847398");
		return Keys.hmacShaKeyFor(key);
	}
	
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
	
	
//	
//	public String getEmailFromToken(String token) {
//		token = token.substring(7);
//		Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token).getBody();
//    	String email = String.valueOf(claims.get("email"));
//    	return email;
//	}
//	
//	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
//		Set<String> auth = new HashSet<>();
//		for(GrantedAuthority authority:authorities) {
//			auth.add(authority.getAuthority());
//		}
//		return String.join(",",auth);
//	}
}
