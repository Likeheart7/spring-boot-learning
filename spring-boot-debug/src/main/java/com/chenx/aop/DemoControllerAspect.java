package com.chenx.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 被切面类切到的bean就会被生成代理对象
 * 这里demoController这个bean在singletonObjects中是CGLIB代理后的对象
 */
@Aspect
@EnableAspectJAutoProxy
@Configuration
public class DemoControllerAspect {
	@Before("execution( * com.chenx.controller.DemoController.*(..))")
	public void beforeAspect() {
		System.out.println("=====>>> enter......");
	}
}
