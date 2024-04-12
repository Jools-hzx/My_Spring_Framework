package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.interface_.BeanPostProcessor;
import com.hzx.myspring.interface_.UsbInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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

        //针对 Camera 的 transferImages() 方法调用 AOP 机制
        if ("camera".equals(beanName) && UsbInterface.class.isAssignableFrom(bean.getClass())) {
            //如果为目标类以及目标方法，则使用动态代理机制，插入前置/后置通知
            return Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(), //给予类加载器
                    bean.getClass().getInterfaces(),        //传入该类对应实现的接口
                    (proxy, method, args) -> {
                        String methodName = method.getName();
                        Object result = null;

                        //针对 transferImages 方法，插入 Aop 通知信息
                        if ("transferImages".equals(methodName)) {
                            AopLogHandler.beforeLog();
                            result = method.invoke(bean, args);
                            AopLogHandler.afterReturnLog();
                        } else {
                            //!!!注意!!! 对于其他方法，也需要返回
                            result = method.invoke(bean, args);
                        }
                        return result;
                    }
            );
        }
        return bean;
    }
}
