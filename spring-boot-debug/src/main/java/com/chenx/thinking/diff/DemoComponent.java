package com.chenx.thinking.diff;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DemoComponent {
    @Bean
    public Example exampleBeanFromComp() {
        Example example = new Example();
        example.setTitle("exampleBean");
        return example;
    }

    @Bean
    public ExampleWrapper exampleWrapperFromComp() {
        ExampleWrapper exampleWrapper = new ExampleWrapper();
        // 直接调用生成新对象，和@Bean注入的单例不是同一个，此行为和@Configuration不同
        exampleWrapper.setExample(exampleBeanFromComp());
        return exampleWrapper;
    }
}
