package com.chenx.thinking.autoconfiguration;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CustomizeConfiguration.class)
public class CustomizeAutoConfiguration {
}
