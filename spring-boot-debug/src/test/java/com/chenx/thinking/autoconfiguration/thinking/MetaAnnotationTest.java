package com.chenx.thinking.autoconfiguration.thinking;

import com.chenx.thinking.metadata.annotation.MetaAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.Set;

//@SpringBootTest
public class MetaAnnotationTest {


    @Test
    public void testGetMetaAnnotation() throws Exception{
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
                System.out.printf("注解 [%s] 元标注 @%s\n",annotationType, metaAnnotationType);
            });
        });
    }
}
