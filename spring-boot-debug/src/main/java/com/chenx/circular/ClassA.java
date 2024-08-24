package com.chenx.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The dependencies of some of the beans in the application context form a cycle:
 *
 * ┌─────┐
 * |  classA (field private com.chenx.circular.ClassB com.chenx.circular.ClassA.b)
 * ↑     ↓
 * |  classB (field private com.chenx.circular.ClassA com.chenx.circular.ClassB.a)
 * └─────┘
 * 默认情况下springboot2.6不支持循环依赖
 * 可以通过在配置文件中
 * spring:
 *   main:
 *     allow-circular-references: true
 */
@Component
public class ClassA {
	@Autowired
	private ClassB b;
}
