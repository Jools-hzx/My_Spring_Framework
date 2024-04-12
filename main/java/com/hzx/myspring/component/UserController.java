package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Autowired;
import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.annotation.Scope;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:21
 * @description: TODO
 */

@Component
@Scope(value = "prototype")
public class UserController {

    @Autowired
    private UserService userService;

    public void m1() {
        System.out.println("UserController 的 m1 方法被调用了....");
        userService.m1();
    }
}
