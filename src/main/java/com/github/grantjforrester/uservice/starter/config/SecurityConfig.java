package com.github.grantjforrester.uservice.starter.config;

import com.github.grantjforrester.spring.security.jwt.HeaderJWTProvider;
import com.github.grantjforrester.spring.security.jwt.JWTAuthenticationFilter;
import com.github.grantjforrester.spring.security.jwt.JWTAuthenticationManager;
import com.github.grantjforrester.spring.security.jwt.StatusCode401AuthenticationEntrypoint;
import com.github.grantjforrester.spring.security.jwt.nimbus.FromSharedSecret;
import com.github.grantjforrester.spring.security.jwt.nimbus.NimbusJWTAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private ApplicationContext context;

    /*
     * Create an authentication filter that looks for a JWT in an "Authorization" header with
     * the authentication scheme "Bearer".  Use the given JWTAuthenticationManager to
     * authenticate the JWT.
     */
    @Bean
    Filter authenticationFilter(JWTAuthenticationManager jwtAuthenticationManager) {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        filter.setJwtProvider(new HeaderJWTProvider("Authorization", "Bearer"));
        filter.setAuthenticationManager(jwtAuthenticationManager);
        filter.setAuthenticationDetailsSource(new WebAuthenticationDetailsSource());
        return filter;
    }

    /*
     * Create a JWTAuthenticationManager bean responsible for verifying the JWT and setting
     * up the SecurityContext.
     */
    @Bean
    JWTAuthenticationManager jwtAuthenticationManager() throws Exception {
        FromSharedSecret keySelector = new FromSharedSecret(jwtSecret);
        return new NimbusJWTAuthenticationManager(keySelector);
    }

    /*
     * Intercept incoming requests with the JWTAuthenticationFilter. If authentication fails
     * return response with status code 401.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter authenticationFilter = (Filter) context.getBean("authenticationFilter");
        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(
                new StatusCode401AuthenticationEntrypoint()
        );
    }
}
