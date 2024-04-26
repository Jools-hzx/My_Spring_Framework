# My_Spring_Framework 模拟实现 Spring 框架机制

## 简介
本项目是一个模拟Spring框架核心功能的示例，包括IoC容器，自动装配，后置处理器，以及AOP代理。

## 核心机制

### 容器配置类 - `HzxSpringConfig`
定义配置类，用于声明组件扫描路径和其他配置。

### 容器 - `HzxSpringApplicationContext`
实现一个简单的IoC容器，负责初始化和管理Bean的生命周期。

### 组件扫描 - `ComponentScan`注解
自定义注解，指定待扫描的包路径，容器将自动注册该路径下的Bean。

### Bean定义 - `BeanDefinition`
封装Bean对象信息，包括作用域和其他元数据。

### Bean作用域 - `@Component` & `@Scope`注解
`@Component`标记一个类为Bean组件，`@Scope`指定Bean实例的作用域（单例或原型）。

### 自动装配 - `@Autowired`注解
自动注入依赖的Bean对象。

### Bean后置处理器 - `InitializingBean` & `BeanPostProcessor`
模拟Spring的Bean后置处理器功能，允许在Bean初始化前后执行自定义操作。

### AOP代理 - `AOP`通知信息类 & `AopBeanPostProcessor`
通过动态代理机制，为目标对象织入横切逻辑，如日志、事务等。

### 启动测试
通过启动测试类验证容器的初始化、Bean的获取和AOP代理的功能。

## 使用说明

### 配置容器
```java
@ComponentScan("com.hzx.myspring.component")
public class HzxSpringConfig {
    // 配置信息...
}
```
### 启动容器
```java
public class Application {
    public static void main(String[] args) {
        HzxSpringApplicationContext context = new HzxSpringApplicationContext(HzxSpringConfig.class);
        // 使用context...
    }
}
```
### 定义Bean
``` java
@Component("myBean")
@Scope("singleton")
public class MyBean {
    // Bean实现...
}
``` 
### 自动装配
```java
public class MyService {
    @Autowired
    private MyBean myBean;
    // 使用myBean...
}
```

### 后置处理器使用
实现InitializingBean和BeanPostProcessor接口，自定义初始化逻辑。

### AOP使用
通过AopBeanPostProcessor为Bean创建动态代理。

### 示例代码
请参考com.hzx.myspring.test包下的测试类进行实例化和使用
