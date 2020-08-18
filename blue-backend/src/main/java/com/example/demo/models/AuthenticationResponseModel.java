package com.example.demo.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

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
	private final Collection<? extends GrantedAuthority> roles;
	private final String token;
}
