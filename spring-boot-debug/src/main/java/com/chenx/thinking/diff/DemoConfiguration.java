package com.chenx.thinking.diff;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * 关于@Component和@Configuration的一些区别
 * 描述：@Component注解修饰的类默认不会被代理，Configuration注解修饰过的类会CGLIB提升，目的是处理内部多个@Bean注解，如果内部调用该@Bean方法名称来创建Bean，实际上
 * 通过CGLIB代理拦截@Bean方法，会将对该方法的调用创建对象，改为直接拿该方法生成的单例来替换。
 * 这样就意味着，内部调用@Bean修饰的方法，实际上获取到的结果是该方法生成的单例对象。
 * 测试见{@link com.chenx.thinking.autoconfiguration.thinking.ComponentAndConfigurationDiffTest}
 * </pre>
 */
@Configuration
public class DemoConfiguration {
    @Bean
    public Example exampleBean() {
        Example example = new Example();
        example.setTitle("exampleBean");
        return example;
    }

    @Bean
    public ExampleWrapper exampleWrapper() {
        ExampleWrapper exampleWrapper = new ExampleWrapper();
        // 实际上因为本类会被代理，这个队exampleBean方法的直接调用会被拦截，替换成@Bean注入的单例
        exampleWrapper.setExample(exampleBean());
        return exampleWrapper;
    }
}
