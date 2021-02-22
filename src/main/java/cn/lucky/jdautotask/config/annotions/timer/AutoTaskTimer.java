package cn.lucky.jdautotask.config.annotions.timer;

import cn.lucky.jdautotask.pojo.enums.TimerGroupType;

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

    //开启状态
    boolean status() default true;

    //分组
    TimerGroupType group() default TimerGroupType.DEFAULT_GROUP;
}
