package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class will hold all the required end-points for this application.
 * 
 * @author ClaudiuIova
 *
 */
@RestController
public class ManageController {

	/**
	 * Login end-point that does not require any token to access it.
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

}
