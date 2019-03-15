package com.spring.blog.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.blog.security.jwt.JwtAuthEntryPoint;
import com.spring.blog.security.jwt.JwtAuthTokenFilter;
import com.spring.blog.security.service.UserDetailsServiceImpl;
 

 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
 
    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;
 
    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }
    
    @Bean
    public RoleHierarchy roleHierarchy() {
      RoleHierarchyImpl r = new RoleHierarchyImpl();
      r.setHierarchy("ROLE_ADMIN > ROLE_PM and ROLE_PM > ROLE_DEVELOPER and ROLE_PM > ROLE_DESIGNER and ROLE_DEVELOPER > ROLE_USER and ROLE_DEVELOPER > ROLE_USER and ROLE_DESIGNER > ROLE_USER");
      return r;
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        .authorizeRequests()
		.antMatchers("/admin/**")
		.hasAnyAuthority("ROLE_ADMIN")
		.antMatchers("/blog/**")
		.hasAnyAuthority("ROLE_USER")
		.antMatchers("/**")
		.permitAll()
		.and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}