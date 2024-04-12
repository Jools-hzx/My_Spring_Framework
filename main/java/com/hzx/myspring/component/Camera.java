package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.interface_.UsbInterface;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/12 20:53
 * @description: TODO
 */

@Component
public class Camera implements UsbInterface {

    @Override
    public boolean transferImages() {
        System.out.println("Fuji-XT-5 传输照片中....");
        return true;
    }

    @Override
    public boolean charge() {
        System.out.println("Fuji-XT-5 传输照片中....");
        return true;
    }
}
