package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.repo.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for users.
 * 
 * @author ClaudiuIova
 *
 */
@Service
@RequiredArgsConstructor
@Async
public class UserService {

	private final UserRepository repo;

	/**
	 * Get all the user form DB
	 * 
	 * @return a {@link Collection} with the users from DB if any.
	 */
	public final CompletableFuture<List<UserDTO>> getUsers() {
		CompletableFuture<List<UserDTO>> thenApply = CompletableFuture.supplyAsync(repo::findAll)
				.thenApply(users -> users.stream().map(this::getUserDTOfromUser).collect(Collectors.toList()));
		return thenApply;
	}

	/**
	 * Save the user in the DB
	 * 
	 * @param user The user that want to be saved.
	 */
	public final UserDTO addUser(final UserDTO userDTO) {
		User user = getUserFromDTO(userDTO);
		User saveAndFlush = repo.saveAndFlush(user);
		return getUserDTOfromUser(saveAndFlush);
	}

	/**
	 * Delete the user from db
	 * 
	 * @param email The email of the user
	 */
	public final void deleteUser(final String email) {
		Optional<User> findByEmail = repo.findByEmail(email);
		findByEmail.ifPresentOrElse((user) -> {
			repo.delete(user);
		}, () -> {
			throw new IllegalArgumentException("Failed to find a user with email: " + email);
		});
	}

	/**
	 * Get the user from the given email address
	 * 
	 * @param email the email address.
	 * @return
	 */
	public final UserDTO getUserByEmail(final String email) {
		Optional<User> findByEmail = repo.findByEmail(email);
		return getUserDTOfromUser(findByEmail.orElseThrow(() -> {
			throw new IllegalArgumentException("Failed to find a user with email: " + email);
		}));
	}

	/**
	 * Update the user group.
	 * 
	 * @param email
	 * @param group
	 * @return the updated user.
	 */
	public final void updateUser(final String email, final String group) {
		this.repo.updateByEmail(email, group);
	}

	private final User getUserFromDTO(final UserDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setGroup(dto.getGroup());
		user.setRole(dto.getRole() != null ? dto.getRole() : "ROLE_USER");
		return user;
	}

	private UserDTO getUserDTOfromUser(final User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		dto.setPassword(user.getPassword());
		dto.setGroup(user.getGroup());
		dto.setRole(user.getRole());
		dto.setId(user.getId());
		return dto;
	}

}
