package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.GroupDTO;
import com.example.demo.entities.Group;
import com.example.demo.repo.GroupRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for groups.
 * 
 * @author ClaudiuIova
 *
 */
@Service
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository repo;

	/**
	 * Get all the groups from the DB.
	 * 
	 * @return a {@link Collection} of groups
	 */
	public final Collection<GroupDTO> getGroups() {
		List<Group> findAll = repo.findAll();
		return findAll.stream().map(this::getDTOfromGroup).collect(Collectors.toList());
	}

	/**
	 * Delete a user from a specific group.
	 * 
	 * @param email
	 */
	public void deleteUserGroupEntry(final String email) {
		repo.deleteGroupEntry(email);
	}

	private GroupDTO getDTOfromGroup(final Group group) {
		GroupDTO dto = new GroupDTO();
		dto.setUserEmail(group.getUserEmail());
		dto.setGroupName(group.getGroupName());
		return dto;
	}

}
