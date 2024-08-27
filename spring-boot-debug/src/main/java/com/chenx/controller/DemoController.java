package com.chenx.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;

@RestController
public class DemoController implements ApplicationContextAware, InitializingBean {
	// DispatcherServlet实例也是作为单例放在单例池中的
	@Autowired
	private DispatcherServlet servlet;

	private ApplicationContext applicationContext;

	@Autowired
	private DemoController demoController;

	@GetMapping("/success")
	public String getInfo() {
		System.out.println(servlet);
		// spring mvc和我们使用的是同一个IOC容器
		System.out.println(applicationContext);
		return "Success!!!";
	}

	/**
	 * 因为springmvc的配置，/**路径处理器，会被配置为默认处理器，如果一个请求没有找到对应的路径处理器，就会使用默认处理器
	 */
	@RequestMapping("/**")
	public String defaultHandler(){
		return "default Handler handle this request";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * InitializingBean 是 Spring 专有的，通常不推荐使用。可以用@PostConstruct替换
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("=============>>> DemoController after properties...");
	}

	@PostConstruct
	private void postConstruct() {
		System.out.println("==========>>> DemoController post construct");
	}
}
