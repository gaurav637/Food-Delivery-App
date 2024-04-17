package com.fooddeliveryapp.Config.jwt;


import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Config.UserServiceConfig;
import com.fooddeliveryapp.Constants.jwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtProvider jProvider;
	
	@Autowired
	private UserServiceConfig userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{    
        String requestHeader = request.getHeader("Authorization");// use constant class
        String username = null;
        String token = null;// Bearer
        if (StringUtils.isEmpty(requestHeader) || !org.apache.commons.lang3.StringUtils.startsWith(requestHeader,"Bearer")){
           filterChain.doFilter(request, response);
           return;
        } 
        token = requestHeader.substring(7);
        username = this.jProvider.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            Boolean validateToken = this.jProvider.validateToken(token, userDetails);
            if (validateToken) {
            	
            	SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            } else {
                logger.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request, response); 
       
    }
}