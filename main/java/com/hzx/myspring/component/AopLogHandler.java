package com.hzx.myspring.component;

import com.hzx.myspring.annotation.Component;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/12 21:00
 * @description: TODO
 */

public class AopLogHandler {

    public static void beforeLog() {
        System.out.println("AopLogHandler 的前置通知 ---- beforeLog");
    }

    public static void afterReturnLog() {
        System.out.println("AopLogHandler 的返回通知 ---- afterReturnLog");
    }
}
