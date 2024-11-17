package com.in28minutes.rest.webservice.refulwebservice.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
		
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		
		System.out.println("inside configure ");
		
//		1) All request should be Authenticated
		http.authorizeRequests(
				auth -> auth.anyRequest().authenticated()
				);
//		2) If request is not authenticated, a web page is shown
		http.httpBasic(Customizer.withDefaults());
//		3) CSRF -> POST, PUT 
		http.csrf().disable();
    }
		
}
