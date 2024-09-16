package com.chenx.thinking.autoconfiguration.thinking;

import com.chenx.thinking.diff.Example;
import com.chenx.thinking.diff.ExampleWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ComponentAndConfigurationDiffTest {
    @Autowired private ApplicationContext context;
    /*
    @Configuration注入的ExampleWrapper中的Example和其中@Bean注入的Example是同一个：true
    @Component注入的ExampleWrapper中的Example和其中@Bean注入的Example是同一个：false
    结论：
    @Configuraiton修饰的类生成的单例会被代理。@Configuration中，会将调用内部@Bean修饰的方法的生成结果替换成@Bean本身创建的单例，@Component不会。
     */
    @Test
    public void testDiff() {
        // @Configuration中@Bean注入到容器中的Example单例
        Example exampleBean = context.getBean("exampleBean", Example.class);
        // @Component中@Bean注入到容器中的Example单例
        Example exampleBeanFromComp = context.getBean("exampleBeanFromComp", Example.class);
        // @Configuration注入的ExampleWrapper
        ExampleWrapper exampleWrapper = context.getBean("exampleWrapper", ExampleWrapper.class);
        // @Component注入的ExampleWrapper
        ExampleWrapper exampleWrapperFromComp = context.getBean("exampleWrapperFromComp", ExampleWrapper.class);
        // 比较@Configuration注入的ExampleWrapper中的Example实例和@Bean注入的是不是同一个
        System.out.println("@Configuration注入的ExampleWrapper中的Example和其中@Bean注入的Example是同一个：" + (exampleBean == exampleWrapper.getExample()));
        // 比较@Component注入的ExampleWrapper中的Example实例和@Bean注入的是不是同一个
        System.out.println("@Component注入的ExampleWrapper中的Example和其中@Bean注入的Example是同一个：" + (exampleBeanFromComp == exampleWrapperFromComp.getExample()));
    }
}
