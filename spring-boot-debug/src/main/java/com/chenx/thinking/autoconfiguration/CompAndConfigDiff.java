package com.chenx.thinking.autoconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于@Component和@Configuration的一些区别
 * 描述：@Component注解修饰的类默认不会被代理，Configuration注解修饰过的类会CGLIB提升，目的是处理内部多个@Bean注解，如果内部调用该@Bean方法名称来创建Bean，实际上
 * 通过CGLIB代理拦截@Bean方法，会将对该方法的调用创建对象，改为直接拿该方法生成的单例来替换。
 * 这样就意味着，内部调用@Bean修饰的方法，实际上获取到的结果是该方法生成的单例对象。
 */
@RestController
public class CompAndConfigDiff {
	@Autowired
	private DemoComponent component;
	@Autowired
	private DemoConfiguration configuration;


	@GetMapping("testDiff")
	public String testDiff(){
		/*
		可以看到@Configuration注解修饰的类是被CGLIB代理过的
		Component修饰的类的类型：[com.chenx.thinking.autoconfiguration.DemoComponent]
		Configuration修饰的类型：[com.chenx.thinking.autoconfiguration.DemoConfiguration$$EnhancerBySpringCGLIB$$24eaec7a]
		 */
		return "Component修饰的类的类型：[" + component.getClass().getName() + "]\n"
				+ "Configuration修饰的类型：[" + configuration.getClass().getName() + "]\n";
	}
}
