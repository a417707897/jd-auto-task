package cn.lucky.jdautotask.pojo.timer;

import lombok.Data;
import org.quartz.Job;

/**
 * 定时任务详情
 */
@Data
public class TimerTaskDetails {

    //定时任务名称
    private String timerName;

    //定时器表达式
    private String cron;

    //定时器说明
    private String timerDesc;

    //分组
    private String group;

    //定时任务状态，0-关闭，1-开启
    private Integer status;

    //任务class
    private Class<Job> jobClass;
}
