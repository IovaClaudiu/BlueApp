package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.models.AuthenticationRequestModel;
import com.example.demo.models.AuthenticationResponseModel;
import com.example.demo.service.UserDetailsServiceImplementation;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for authentication
 * 
 * @author ClaudiuIova
 *
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public final class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsServiceImplementation userDetailsService;
	private final UserService userService;
	private final JWTUtil jwtTokenUtil;

	@PostMapping("/register")
	public final ResponseEntity<?> register(@RequestBody @Validated final UserDTO user) {
		try {
			userService.addUser(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/authenticate")
	public final ResponseEntity<?> authenticate(@RequestBody final AuthenticationRequestModel authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		UserDTO userByEmail = this.userService.getUserByEmail(userDetails.getUsername());

		return ResponseEntity
				.ok(new AuthenticationResponseModel(userDetails.getUsername(), userByEmail.getRole(), jwt));
	}

}
