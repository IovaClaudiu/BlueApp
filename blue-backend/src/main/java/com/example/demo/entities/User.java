package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	@Column(name = "[role]")
	private String role;

	@Column(name = "[group]")
	private String group;
}
