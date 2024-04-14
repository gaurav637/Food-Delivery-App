package com.fooddeliveryapp.Config.jwt;


import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fooddeliveryapp.Constants.jwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        // Bearer
        String requestHeader = request.getHeader(jwtConstants.JWT_HEADER);
        String token = null;
        if(requestHeader != null && requestHeader.startsWith("Bearer")) {
            //looking good
            token = requestHeader.substring(7).trim();
            try {
                 
            	SecretKey key = Keys.hmacShaKeyFor(jwtConstants.SECRET_KEY.getBytes());
            	Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
            	String email = String.valueOf(claims.get("email"));
            	String authorities = String.valueOf((claims.get("authorities")));
            	List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);//converts string to grantedAuthority
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, auth);	
            	SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            }catch (JwtException e) {
                logger.error("Error parsing JWT token: " + e.getMessage());
                // Handle JWT exception, such as token expiration, invalid signature, etc.
                // For example, you can return a specific error response to the client
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            catch (Exception e) {
                logger.info("Invalid Header Value  !!!!");
                e.printStackTrace();
            }
            
        } 
        else {
            logger.info("Invalid Header Value !!! ");
        }
 
        try {
			filterChain.doFilter(request, response);
		} catch (java.io.IOException | ServletException e) {
			e.printStackTrace();
		}    
    }
}