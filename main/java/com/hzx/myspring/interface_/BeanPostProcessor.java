package com.hzx.myspring.interface_;

public interface BeanPostProcessor {

    //bean 初始化前执行的业务
    Object postProcessBeforeInitialization(Object bean, String beanName);
    //bean 初始化后执行的业务
    Object postProcessAfterInitialization(Object bean, String beanName);
}
