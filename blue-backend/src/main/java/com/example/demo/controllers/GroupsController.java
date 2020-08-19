package com.example.demo.controllers;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GroupDTO;
import com.example.demo.service.GroupService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing the groups.
 * 
 * @author ClaudiuIova
 * @return
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
}
