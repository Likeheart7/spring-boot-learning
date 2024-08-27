package com.chenx.initconfig;

import com.chenx.controller.DemoController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/*
执行顺序
============>>> demoController | postProcessBeforeInitialization
==========>>> DemoController post construct
=============>>> DemoController after properties...
============>>> demoController | postProcessAfterInitialization
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
	// 每个bean都会执行,可以自定义过滤
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof DemoController) {
			System.out.println("============>>> " + beanName + " | postProcessBeforeInitialization");
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	// 每个bean都会执行,可以自定义过滤
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof DemoController) {
			System.out.println("============>>> " + beanName + " | postProcessAfterInitialization");
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
}
