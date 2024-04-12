package com.hzx.myspring.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Autowired {

    //是否必须要完成装配，true 表示必须; false 表示可以为 null
    boolean required() default true;
}
