package com.hzx.myspring.interface_;

public interface UsbInterface {

    default boolean transferImages() {
        return false;
    }

    default boolean charge() {
        return false;
    }
}
