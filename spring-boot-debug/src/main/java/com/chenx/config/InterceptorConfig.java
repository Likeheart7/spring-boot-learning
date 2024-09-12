package com.chenx.config;

import com.chenx.interceptor.DemoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	private DemoInterceptor interceptor;

	/**
	 * 添加一个全路径拦截器，用于测试{@link DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)} 中getHandler返回值是否包含拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/success");
	}
}
