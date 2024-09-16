package com.chenx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringBootDebugApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDebugApplication.class, args);
	}
	// 应用启动后自动调用，输出当前webServer的类型。
	// 如果应用不是web应用，注入webServer就是null，该回调方法就有问题，所以可以用监听ServletWebServerInitializedEvent事件替代，
//    @Bean
//    public CommandLineRunner runner(WebServerApplicationContext webServer) {
//        // =====>>> 当前web实现为：[org.springframework.boot.web.embedded.jetty.JettyWebServer]
//        return args -> System.out.println("=====>>> 当前web实现为：[" + webServer.getWebServer().getClass().getName() + "]");
//    }

	@EventListener(ServletWebServerInitializedEvent.class)
	public void onServletWebServerInitialized(WebServerInitializedEvent event) {
		// =====>>> 当前web实现为：[org.springframework.boot.web.embedded.jetty.JettyWebServer]
		System.out.println("=====>>> 当前web实现为：[" + event.getWebServer().getClass().getName() + "]");
	}
}