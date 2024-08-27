package com.chenx.initconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CustomInitializingBean implements InitializingBean {
	// 本类属性设置完成执行
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("=============>>> CustomInitializingBean after properties...");
	}
}
