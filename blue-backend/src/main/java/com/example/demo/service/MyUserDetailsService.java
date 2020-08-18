package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.MyUserDetails;
import com.example.demo.models.User;
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
		Optional<User> findByUsername = repo.findByEmail(username);

		findByUsername.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

		return findByUsername.map(MyUserDetails::new).get();
	}

	/**
	 * Get all the user form DB
	 * 
	 * @return a {@link Collection} with the users from DB if any.
	 */
	public final Collection<User> getUsers() {
		return repo.findAll();
	}
}
