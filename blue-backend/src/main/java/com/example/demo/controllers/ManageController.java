package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.models.AuthenticationRequestModel;
import com.example.demo.models.AuthenticationResponseModel;
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

	@GetMapping("/user")
	public final String user() {
		return "Hi User";
	}

	@GetMapping("/admin")
	public final String admin() {
		return "Hi Admin";
	}

	@PostMapping("/authenticate")
	public final ResponseEntity<?> createAuthenticationToken(
			@RequestBody final AuthenticationRequestModel authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity
				.ok(new AuthenticationResponseModel(userDetails.getUsername(), userDetails.getAuthorities(), jwt));
	}

}