package com.chenx.thinking.autoconfiguration;

import com.chenx.thinking.domain.Example;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 编写自己的spring.factories文件
@Configuration
// Tomcat存在时才配置
@ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
//@ConditionalOnClass(name = "org.eclipse.jetty.server.Server")
public class CustomizeConfiguration {
	@Bean
	public Example customizeAutoConfigurationExample() {
		Example example = new Example();
		example.setTitle("条件装配示例");
		example.setDesc("当容器为Tomcat时，装配此bean");
		return example;
	}
}
