package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class UserService {

	private final UserRepository repo;
	private final GroupService groupRepo;

	/**
	 * Get all the user form DB
	 * 
	 * @return a {@link Collection} with the users from DB if any.
	 */
	public final Collection<UserDTO> getUsers() {
		List<User> findAll = repo.findAll();
		return findAll.stream().map(this::getUserDTOfromUser).collect(Collectors.toList());
	}

	/**
	 * Save the user in the DB
	 * 
	 * @param user The user that want to be saved.
	 */
	public final void addUser(final UserDTO userDTO) {
		User user = getUserFromDTO(userDTO);
		repo.saveAndFlush(user);
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
			groupRepo.deleteUserGroupEntry(email);
		}, () -> {
			throw new IllegalArgumentException("Failed to find a user with email: " + email);
		});
	}

	private final User getUserFromDTO(final UserDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setRoles(dto.getRoles());
		return user;
	}

	private UserDTO getUserDTOfromUser(final User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		dto.setPassword(user.getPassword());
		dto.setRoles(user.getRoles());
		dto.setId(user.getId());
		return dto;
	}

}
