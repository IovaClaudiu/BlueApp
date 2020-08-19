package com.example.demo.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Model for the response.
 * 
 * @author ClaudiuIova
 *
 */
@RequiredArgsConstructor
@Getter
public final class AuthenticationResponseModel {

	private final String email;
	private final String role;
	private final String token;
}
