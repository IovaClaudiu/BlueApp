package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
public final class GroupService {

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
	 * Add a new group to DB
	 * 
	 * @param groupDto
	 * @return
	 */
	public final GroupDTO addGroup(GroupDTO groupDto) {
		Group groupFromDTO = getGroupFromDTO(groupDto);
		Group saveAndFlush = repo.saveAndFlush(groupFromDTO);
		return getDTOfromGroup(saveAndFlush);
	}

	/**
	 * Remove the group from DB
	 * 
	 * @param groupDTO
	 */
	public final void removeGroup(String groupName) {
		Optional<Group> findByGroupName = repo.findByGroupName(groupName);
		findByGroupName.ifPresentOrElse((group) -> {
			repo.delete(group);
		}, () -> {
			throw new IllegalArgumentException("Failed to find group with name: " + groupName);
		});
	}

	private GroupDTO getDTOfromGroup(final Group group) {
		GroupDTO dto = new GroupDTO();
		dto.setGroupName(group.getGroupName());
		return dto;
	}

	private Group getGroupFromDTO(final GroupDTO groupDTO) {
		Group group = new Group();
		group.setGroupName(groupDTO.getGroupName());
		return group;
	}

}
