package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import com.example.demo.entities.Group;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for {@link Group} entity.
 * 
 * @author ClaudiuIova
 *
 */
@Getter
@Setter
public class GroupDTO {

	@NotNull
	private String groupName;

}
