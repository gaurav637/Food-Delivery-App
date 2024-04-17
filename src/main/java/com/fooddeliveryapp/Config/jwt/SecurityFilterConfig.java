package com.fooddeliveryapp.Config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fooddeliveryapp.Config.UserServiceConfig;
import com.fooddeliveryapp.Config.UserServiceConfigImple;
import com.fooddeliveryapp.Model.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {
    private JwtAuthenticationEntryPoint point;
    
    @Autowired   
    private JwtAuthenticationFilter filter;
    
    @Autowired
    private UserServiceConfig uConfig;

    @Autowired
    private  UserServiceConfigImple userServiceImple;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                		.requestMatchers("/api/v1/admin").hasAnyAuthority(USER_ROLE.ADMIN.name())
                		.requestMatchers("/api/v1/user").hasAnyAuthority(USER_ROLE.USER.name())
                		.anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        if (uConfig != null) {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(uConfig.userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        } else {
            
            throw new IllegalStateException("User service configuration is null.");
        }
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
		return configuration.getAuthenticationManager();
	}
}