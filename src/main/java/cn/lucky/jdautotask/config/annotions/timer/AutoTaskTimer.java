package cn.lucky.jdautotask.config.annotions.timer;

import java.lang.annotation.*;

/*
 * @Author zyl
 * @Description 自动任务定时器注解
 * @Date 2021/1/18 17:11
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AutoTaskTimer {

    //定时器表达式
    String cron();

    //定时器名称
    String timerName() default "";

    //定时器说明
    String timerDesc() default "";

    //分组
    String group() default "DEFAULT_GROUP";
}
