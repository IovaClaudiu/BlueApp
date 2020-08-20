package com.example.demo.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing the users.
 * 
 * @author ClaudiuIova
 *
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

	private final UserService userService;

	@GetMapping(path = "/users")
	public final ResponseEntity<?> users() {
		try {
			CompletableFuture<List<UserDTO>> users = this.userService.getUsers();
			return ResponseEntity.ok(users.get());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/users/{email}")
	public final ResponseEntity<?> delete(@PathVariable("email") final String email) {
		try {
			userService.deleteUser(email);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@PutMapping("/users/{email}")
	public final ResponseEntity<?> updateUser(@PathVariable("email") final String email,
			@RequestBody final GroupDTO group) {
		try {
			userService.updateUser(email, group.getGroupName());
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
