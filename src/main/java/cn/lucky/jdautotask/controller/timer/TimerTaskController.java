package cn.lucky.jdautotask.controller.timer;


import cn.hutool.core.date.DateUtil;
import cn.lucky.jdautotask.config.timer.ScanTimerTaskConfig;
import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.*;

/*
 * @Author zyl
 * @Description 定时器管理类
 * @Date 2021/1/18 17:24
 **/
@RestController
@RequestMapping("/timer/task")
@Validated
@Log4j2
public class TimerTaskController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScanTimerTaskConfig scanTimerTaskConfig;

    private Map<String,TimerTaskDetails> timerTaskDetailsMap;

    /**
     * 开启定时任务
     * @param taskName
     * @return
     */
    @GetMapping("/start")
    public String start(@NotNull(message = "taskName不能为空") String taskName) {

        TimerTaskDetails timerTaskDetails = timerTaskDetailsMap.get(taskName);
        if (timerTaskDetails == null) {
            return "无此定时器";
        }
        try {

            log.info("开始设置：{}定时器，定时任务详情：{}", timerTaskDetails.getTimerName(), timerTaskDetails.getTimerDesc());
            //用于创建任务
            JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
            //设置定时任务名称
            jobDetailFactoryBean.setBeanName(timerTaskDetails.getTimerName());

            //设置组，猜测是给定时任务按照作用分组，这里我们直接默认就好了，后面有需要在调整
            jobDetailFactoryBean.setGroup(timerTaskDetails.getGroup());
            //设置定时任务class
            jobDetailFactoryBean.setJobClass(timerTaskDetails.getJobClass());
            //传参数的，执行的时候传给要执行的方法
            jobDetailFactoryBean.setJobDataMap(null);
            //填入上面设置数据，因为我们是一个工厂
            jobDetailFactoryBean.afterPropertiesSet();

            //创建一个调度器工厂
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            //设置cron表达式
            cronTriggerFactoryBean.setCronExpression(timerTaskDetails.getCron());
            //设置定时器名称
            cronTriggerFactoryBean.setBeanName(timerTaskDetails.getTimerName());
            //设置调度器名称
            cronTriggerFactoryBean.setName(timerTaskDetails.getTimerName());
            //设置分组
            cronTriggerFactoryBean.setGroup(timerTaskDetails.getGroup());
            //生成属性
            cronTriggerFactoryBean.afterPropertiesSet();
            //提交任务
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(), cronTriggerFactoryBean.getObject());
            log.info("设置：{}定时器成功", timerTaskDetails.getTimerName());
            timerTaskDetails.setStatus(1);
            return "设置定时器成功";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("设置：{}定时器异常", timerTaskDetails.getTimerName());
            return "设置定时器异常";
        }
    }

    @GetMapping("/stop")
    public String stop(@NotNull(message = "taskName不能为空") String taskName){


        try {
            TimerTaskDetails timerTaskDetails = timerTaskDetailsMap.get(taskName);
            scheduler.deleteJob(JobKey.jobKey(taskName, timerTaskDetails.getGroup()));
            timerTaskDetails.setStatus(0);
            return "停止定时器成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "停止定时器异常";
        }
    }

    @GetMapping("/updateCron")
    public String updateCron(@NotNull(message = "taskName不能为空") @RequestParam("taskName") String taskName,
                             @NotNull(message = "cron不能为空") @RequestParam("cron") String cron){
        try {
            stop(taskName);
            TimerTaskDetails timerTaskDetails = timerTaskDetailsMap.get(taskName);
            timerTaskDetails.setCron(cron);
            start(taskName);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "通知定时器异常";
        }
    }

    /**
     * 获取可执行定时任务详情
     * @return
     */
    @GetMapping("/jobDetails")
    public Map<String,TimerTaskDetails> getJobDetails(){
        return timerTaskDetailsMap;
    }

    /**
     * 初始化定时任务
     */
    @PostConstruct
    public void init(){
        timerTaskDetailsMap = scanTimerTaskConfig.getTimerTaskDetailsList();
        timerTaskDetailsMap.forEach((key,value)->{
            start(key);
        });
    }
}
