package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.MyUserDetails;
import com.example.demo.models.UserEntity;
import com.example.demo.repo.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * User detail service class.
 * 
 * @author ClaudiuIova
 *
 */
@Service
@RequiredArgsConstructor
public final class MyUserDetailsService implements UserDetailsService {

	private final UserRepository repo;

	@Override
	public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Optional<UserEntity> findByUsername = repo.findByUsername(username);

		findByUsername.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

		return findByUsername.map(MyUserDetails::new).get();
	}
}
