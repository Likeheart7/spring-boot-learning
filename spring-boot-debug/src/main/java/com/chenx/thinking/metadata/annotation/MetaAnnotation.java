package com.chenx.thinking.metadata.annotation;


import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 测试获取注解的元注解信息，层次获取，包括所有从元注解继承来的
 * 见{@link com.chenx.thinking.autoconfiguration.thinking.MetaAnnotationTest}
 */
@RestControllerAdvice(value = "com.chenx")
public class MetaAnnotation {

}
