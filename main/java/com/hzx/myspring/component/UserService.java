package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Autowired;
import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.annotation.Scope;
import com.hzx.myspring.interface_.InitializingBean;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:21
 * @description: TODO
 */

@Component
@Scope
public class UserService implements InitializingBean {

    @Autowired(required = true)
    private UserDao userDao;

    public void m1() {
        System.out.println("UserService 的 m1 方法被调用了...");
        userDao.m1();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService bean 对象被初始化.....");
    }
}
