package com.hzx.myspring.test;

import com.hzx.myspring.application.HzxSpringApplicationContext;
import com.hzx.myspring.component.UserDao;
import com.hzx.myspring.config.HzxSpringConfig;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:23
 * @description: TODO
 */
public class ApplicationTest {

    public static void main(String[] args) {

        HzxSpringApplicationContext ioc = new HzxSpringApplicationContext(HzxSpringConfig.class);

        System.out.println("容器注入完成!");

        Object bean = ioc.getBean("userDao");
        UserDao userDao = ioc.getBean("userDao", UserDao.class);

        System.out.println(bean);
        System.out.println(userDao);
        System.out.println(bean == userDao);
    }
}
