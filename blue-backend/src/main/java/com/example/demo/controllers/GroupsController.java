package com.example.demo.controllers;

import java.util.Collection;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GroupDTO;
import com.example.demo.service.GroupService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing the groups.
 * 
 * @author ClaudiuIova
 *
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GroupsController {

	private final GroupService groupService;

	@GetMapping("/groups")
	public final ResponseEntity<?> groups() {
		try {
			Collection<GroupDTO> groups = groupService.getGroups();
			return ResponseEntity.ok(groups);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/groups")
	public final ResponseEntity<?> addGroup(@RequestBody final GroupDTO groupName) {
		try {
			GroupDTO addGroup = groupService.addGroup(groupName);
			return ResponseEntity.ok(addGroup);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/groups/{group}")
	public final ResponseEntity<?> deleteGroup(@PathVariable("group") final String group) {
		try {
			this.groupService.removeGroup(group);
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("The selected group: " + group + " cannot be deleted because it's assign to a user!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
