package com.chenx.controller;

import com.chenx.service.DemoService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
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

	@Autowired
	private DemoService demoService;

	private ApplicationContext applicationContext;

	@Autowired
	private DemoController demoController;

	@GetMapping("/success")
	public String getInfo() {
		System.out.println(servlet);
		// 在springboot中，spring mvc和我们使用的是同一个IOC容器
		// 如果是spring + spring mvc的项目中，spring和springmvc各自持有一个ioc容器
		// 且spring的容器是mvc容器的父容器，所以mvc可以访问spring容器中的内容，而spring不能访问mvc容器中的内容
		// 所以spring+springmvc项目中，spring容器中可能没有Controller对应的bean（道听途说 如下）
		/*
		有一篇博客说，Spring MVC和Spring本身用的不是一个IoC容器，Spring 的IoC容器是MVC的IoC容器的父容器
	    所以MVC可以访问Spring的IoC容器，而Spring不能访问MVC的IoC容器，结果就是Controller可以访问Service对象，
	    而Service对象不能访问Controller对象
		 */
		System.out.println(applicationContext);
		return "Success!!!";
	}


	/**
	 * 因为springmvc的配置，/**路径处理器，会被配置为默认处理器，如果一个请求没有找到对应的路径处理器，就会使用默认处理器
	 */
	@RequestMapping("/**")
	public String defaultHandler(){
		// 两个都找不到，但是可以注入，这就是依赖注入和依赖查找的区别之一
//		System.out.println(applicationContext.getBean(BeanFactory.class));
//		System.out.println(applicationContext.getBean(ApplicationContext.class));
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
