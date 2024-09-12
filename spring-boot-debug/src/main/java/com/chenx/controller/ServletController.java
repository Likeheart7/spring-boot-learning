package com.chenx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Map;

@RestController
public class ServletController {

	@Autowired
	private ServletContext servletContext;

	@GetMapping("servlet")
	public void servlet() {
		// {dispatcherServlet=org.apache.catalina.core.ApplicationServletRegistration@3ce13638}
		Map<String, ? extends ServletRegistration> servletRegistrations = servletContext.getServletRegistrations();
		System.out.println(servletRegistrations);
		System.out.println(servletContext);
	}

	/**
	 * SB会自动对方法参数进行填充
	 * @param controller SB填充进来的DemoController实例
	 */
	@GetMapping("/autowire")
	public String autowire(DemoController controller) {
		System.out.println(controller);
		return controller.toString();
	}

}
