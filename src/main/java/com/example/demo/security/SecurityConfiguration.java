package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filters.JwtRequestFilter;
import com.example.demo.service.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * Security configuration class.
 * 
 * @author ClaudiuIova
 *
 */
@SuppressWarnings("deprecation")
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final MyUserDetailsService myUserDetailsService;
	private final JwtRequestFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll()//
				.antMatchers("/admin").hasRole("ADMIN")//
				.antMatchers("/user").hasAnyRole("ADMIN", "USER")//
				.anyRequest().authenticated()//
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * We need this password encoder in order to use the spring security, The
	 * {@link NoOpPasswordEncoder} it's just for test purpose in order to use the
	 * plain text as password.
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * We need to provide this as a bean in order for Spring IoC container to pick
	 * it up.
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
