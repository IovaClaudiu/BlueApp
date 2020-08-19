package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.models.MyUserDetails;
import com.example.demo.repo.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * User detail service class used for spring security auth.
 * 
 * @author ClaudiuIova
 *
 */
@Service
@RequiredArgsConstructor
public final class UserDetailsServiceImplementation implements UserDetailsService {

	private final UserRepository repo;

	@Override
	public final UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Optional<User> findByUsername = repo.findByEmail(email);

		findByUsername.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + email));

		return findByUsername.map(MyUserDetails::new).get();
	}
}
