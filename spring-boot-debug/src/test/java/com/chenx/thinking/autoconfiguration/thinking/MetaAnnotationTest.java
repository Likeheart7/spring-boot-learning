package com.chenx.thinking.autoconfiguration.thinking;

import com.chenx.thinking.metadata.annotation.MetaAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

//@SpringBootTest
public class MetaAnnotationTest {


	@Test
	public void testGetMetaAnnotation() throws Exception {
		String className = MetaAnnotation.class.getName();
		CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
		// 读取@RestController MetaDataReader信息
		MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(className);
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        /*
        注解 [org.springframework.web.bind.annotation.RestControllerAdvice] 元标注 @org.springframework.web.bind.annotation.ControllerAdvice
        注解 [org.springframework.web.bind.annotation.RestControllerAdvice] 元标注 @org.springframework.web.bind.annotation.ResponseBody
        注解 [org.springframework.web.bind.annotation.RestControllerAdvice] 元标注 @org.springframework.stereotype.Component
        注解 [org.springframework.web.bind.annotation.RestControllerAdvice] 元标注 @org.springframework.stereotype.Indexed
         */
		annotationMetadata.getAnnotationTypes().forEach(annotationType -> {
			Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes(annotationType);
			metaAnnotationTypes.forEach(metaAnnotationType -> {
				System.out.printf("注解 [%s] 元标注 @%s\n", annotationType, metaAnnotationType);
			});
		});
	}

	/**
	 * 通过反射获取注解的属性
	 */
	@Test
	public void testGetAnnotationAttr() {
		Class<MetaAnnotation> originClass = MetaAnnotation.class;
		RestControllerAdvice annotation = originClass.getAnnotation(RestControllerAdvice.class);
		ReflectionUtils.doWithMethods(RestControllerAdvice.class,
				method -> System.out.printf("@TransactionalService.%s() = %s\n", method.getName(),
				// 执行反射方法调用
				ReflectionUtils.invokeMethod(method, annotation)),
				// 过滤Annotation接口定义的方法
				method -> !method.getDeclaringClass().equals(Annotation.class)
		);
		// [com.chenx]
		System.out.println(Arrays.toString(annotation.value()));

	}
}
