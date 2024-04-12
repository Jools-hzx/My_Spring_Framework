package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.interface_.BeanPostProcessor;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/12 18:08
 * @description: TODO
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("MyBeanPostProcessor -- postProcessBeforeInitialization 方法 ---- 被调用 !!!");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("MyBeanPostProcessor -- postProcessAfterInitialization 方法 ---- 被调用 !!!");
        return bean;
    }
}
