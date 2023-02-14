package cn.zeroeden.interfaceBrushProtection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 接口防刷
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    /**
     * 秒
     * @return 多少秒内
     */
    long second() default 5L;

    /**
     * 最大访问次数
     * @return 最大访问次数
     */
    long maxTime() default 3L;

    /**
     * 禁用时长，单位/秒
     * @return 禁用时长
     */
    long forbiddenTime() default 120L;
}
