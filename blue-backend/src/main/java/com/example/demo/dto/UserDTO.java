package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import com.example.demo.entities.User;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for {@link User} entity.
 * 
 * @author ClaudiuIova
 *
 */
@Getter
@Setter
public class UserDTO {

	private long id;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@NotNull
	private String email;

	@NotNull
	private String password;

	private String roles;

}
