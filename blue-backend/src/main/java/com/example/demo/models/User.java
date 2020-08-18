package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * User entity class.
 * 
 * @author ClaudiuIova
 *
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

	@Id
	private long id;

	private String email;

	private String firstname;

	private String lastname;

	private String password;

	private String roles;
}
