package com.chenx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

@RestController
public class DemoController {
	// DispatcherServlet实例也是作为单例放在单例池中的
	@Autowired
	private DispatcherServlet servlet;

	@GetMapping("/success")
	public String getInfo() {
		System.out.println(servlet);
		return "Success!!!";
	}
}
