package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.interface_.BeanPostProcessor;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/12 18:09
 * @description: TODO
 */

@Component
public class AopBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("AopBeanPostProcessor -- postProcessBeforeInitialization -- 被调用!!!!");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("AopBeanPostProcessor -- postProcessAfterInitialization -- 被调用!!!!");
        return bean;
    }
}
