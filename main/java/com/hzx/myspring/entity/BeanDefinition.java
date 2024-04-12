package com.hzx.myspring.entity;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/12 15:37
 * @description: TODO
 */
public class BeanDefinition {

    //存放 Bean Id
    private String beanId;

    //存放 Bean 的 Class 类对象
    private Class<?> cls;

    //存放 Bean 定义的 scope
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public BeanDefinition() {
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
