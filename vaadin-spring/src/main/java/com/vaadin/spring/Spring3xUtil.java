package com.vaadin.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * Created by renner on 2016.02.12..
 */
public class Spring3xUtil {

    private ConfigurableListableBeanFactory beanFactory;

    public Spring3xUtil(ApplicationContext wac) {
        if (!(wac instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalStateException("dont know what todo...");
        }
        beanFactory = (ConfigurableListableBeanFactory)wac;
    }

    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        ArrayList<String> results = new ArrayList<String>();

        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (!beanDefinition.isAbstract() && (beanFactory.findAnnotationOnBean(beanName, annotationType) != null)) {
                results.add(beanName);
            }
        }

        for (String beanName : beanFactory.getSingletonNames()) {
            if (!results.contains(beanName) && (beanFactory.findAnnotationOnBean(beanName, annotationType) != null)) {
                results.add(beanName);
            }
        }
        return results.toArray(new String[results.size()]);
    }
}
