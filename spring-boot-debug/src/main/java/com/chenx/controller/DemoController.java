package com.chenx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	@GetMapping("/success")
	public String getInfo() {
		return "Success!!!";
	}
}
