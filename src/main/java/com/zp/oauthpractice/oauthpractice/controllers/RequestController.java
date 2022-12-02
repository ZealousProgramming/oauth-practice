package com.zp.oauthpractice.oauthpractice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
	
	@GetMapping(path = "/")
	public String root() {
		return "root";
	}

	@GetMapping(path = "/users")
	public String users() {
		return "users";
	}

	@GetMapping(path = "/admin")
	public String admin() {
		return "admin";
	}
}

