package com.tyh.aaron.core;

import com.tyh.aaron.boot.AppLaunch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnnType {
    AppLaunch.ObserverType observerType();
    String taskType() default "*";

}
