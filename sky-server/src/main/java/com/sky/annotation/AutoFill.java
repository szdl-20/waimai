package com.sky.annotation;
/*
 * 
 * 
 * 自定义注解，用于表示某个方法需要进行功能字段自动填充处理
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sky.enumeration.OperationType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //定义公共字段的操作

    OperationType value();

}