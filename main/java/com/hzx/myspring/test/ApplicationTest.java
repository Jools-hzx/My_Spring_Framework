package com.hzx.myspring.test;

import com.hzx.myspring.application.HzxSpringApplicationContext;
import com.hzx.myspring.component.UserController;
import com.hzx.myspring.config.HzxSpringConfig;
import com.hzx.myspring.interface_.UsbInterface;


/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:23
 * @description: TODO
 */
public class ApplicationTest {

    public static void main(String[] args) {

        HzxSpringApplicationContext ioc = new HzxSpringApplicationContext(HzxSpringConfig.class);

        UsbInterface camera = (UsbInterface) ioc.getBean("camera");
        camera.transferImages();

        UserController controller = ioc.getBean("userController", UserController.class);
        controller.m1();

        camera.transferImages();

//        UserController bean = ioc.getBean("userController", UserController.class);
//        bean.m1();

//        Object bean = ioc.getBean("userDao");
//        UserDao userDao = ioc.getBean("userDao", UserDao.class);
//
//        System.out.println(bean);
//        System.out.println(userDao);
//        System.out.println(bean == userDao);
    }
}
