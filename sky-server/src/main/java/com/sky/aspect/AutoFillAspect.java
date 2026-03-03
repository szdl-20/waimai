package com.sky.aspect;
/*
 * 
 * 
 * 
 * 自定义切面，实现公共字段自动填充
 */


import java.lang.reflect.Method;
import java.security.Signature;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autopointcut(){

    }
    @Before("autopointcut()")
    public void autofill(JoinPoint joinPoint){
        log.info("开始进行公共字段填充");

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();


        Object[] args = joinPoint.getArgs();
        if( args == null || args.length == 0 ){
            return ;
        }
        Object entity = args[0];
        
        if( operationType == OperationType.UPDATE){
            try{
                 Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

            setUpdateUser.invoke(entity, BaseContext.getCurrentId());
            setUpdateTime.invoke(entity, LocalDateTime.now());

            }catch( Exception e){
                e.printStackTrace();
            }
                }
        else if( operationType == OperationType.INSERT){
            try{
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
            setUpdateUser.invoke(entity, BaseContext.getCurrentId());
            setUpdateTime.invoke(entity, LocalDateTime.now());
            setCreateTime.invoke(entity, LocalDateTime.now());
            setCreateUser.invoke(entity, BaseContext.getCurrentId());

            }catch( Exception e){
                e.printStackTrace();
            }

        }

    }

    
}