package com.chenx.thinking.autoconfiguration;


import com.chenx.thinking.domain.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CustomizeAutoConfigurationTest {

	@Autowired
	private ApplicationContext context;

	@Test
	public void testCustomizeAutoConfiguration() {
		System.out.println(context.getBean("customizeAutoConfigurationExample", Example.class));
	}

}