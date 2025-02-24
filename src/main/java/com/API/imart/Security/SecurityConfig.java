package com.API.imart.Security;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.API.imart.repository.SellerRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private final SellerRepository Srepo;

	public SecurityConfig(SellerRepository Srepo) {
//		super();
		this.Srepo = Srepo;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> Srepo.findByUsername(username).map(
				buyer -> (UserDetails) User.withUsername(buyer.getUsername()).password(buyer.getPassword()).build())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Encrypt passwords
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// Security Filter Chain Bean

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()); 

		http.authorizeHttpRequests(authz -> authz.requestMatchers("/api/auth/login", "/api/auth/register","/api/**").permitAll());

		// Allow public access to register
		http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated()); // All other requests require
																					// authentication

		/*
		 * http.formLogin(form -> form .loginPage("/login") // Custom login page
		 * .loginProcessingUrl("/login") .successHandler(new
		 * CustomLoginSuccessHandler()) .permitAll()); // Allow access to the login page without authentication
		   .logout().permitAll();
		 */

		return http.build();
	}

}
