package com.example.demo.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class AuthenticationResponse {

	private final String username;
	private final String jwt;
}
