package cn.lucky.jdautotask.service.timer.impl;

import cn.lucky.jdautotask.config.timer.ScanTimerTaskConfig;
import cn.lucky.jdautotask.handle.common.Result;
import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
import cn.lucky.jdautotask.service.timer.TimerTaskService;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

@Service
@Log4j2
public class TimerTaskServiceImpl implements TimerTaskService {

    @Autowired
    private Scheduler scheduler;


    @Autowired
    private ScanTimerTaskConfig scanTimerTaskConfig;

    @Override
    public Result start(TimerTaskDetails timerTaskDetails) {
        log.info("开始设置：{}定时器，定时任务详情：{}", timerTaskDetails.getTimerName(), timerTaskDetails.getTimerDesc());
        try {
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
            return Result.success("设置定时器成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("设置：{}定时器异常", timerTaskDetails.getTimerName());
            throw new RuntimeException("设置："+timerTaskDetails.getTimerName()+"定时器异常");
        }
    }

    @Override
    public Result stop(String taskName) {
        try {
            TimerTaskDetails timerTaskDetails = scanTimerTaskConfig.getTimerTaskDetailsMap().get(taskName);
            scheduler.deleteJob(JobKey.jobKey(taskName, timerTaskDetails.getGroup()));
            timerTaskDetails.setStatus(false);
            return Result.success("停止定时器成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("停止定时器异常");
        }
    }
}
