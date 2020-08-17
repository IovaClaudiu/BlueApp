package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.models.AuthenticationRequest;
import com.example.demo.models.AuthenticationResponse;
import com.example.demo.service.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * This class will hold all the required end-points for this application.
 * 
 * @author ClaudiuIova
 *
 */
@RestController
@RequiredArgsConstructor
public final class ManageController {

	private final AuthenticationManager authenticationManager;
	private final MyUserDetailsService userDetailsService;
	private final JWTUtil jwtTokenUtil;

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/hello")
	public final String hello() {
		return "hello";
	}

	@PostMapping("/authenticate")
	public final ResponseEntity<?> createAuthenticationToken(
			@RequestBody final AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), jwt));
	}

}
