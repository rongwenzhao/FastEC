package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rongwenzhao on 2017/11/23.
 */
@Target(ElementType.TYPE)//此注解作用在类上
@Retention(RetentionPolicy.SOURCE)//源码阶段，编译器处理;打包成apk后就不再使用了，好处就是对性能没影响
public @interface PayEntryGenerator {
    String packageName();

    Class<?> payEntryTemplate();
}
