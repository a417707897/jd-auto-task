package cn.lucky.jdautotask.config.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扫描定时器任务，继承job带AutoTaskTimer注解的
 */
@Component
@Log4j2
public class ScanTimerTaskConfig {

    private Map<String,TimerTaskDetails> timerTaskDetailsMap;

    public ScanTimerTaskConfig(){
        timerTaskDetailsMap = new ConcurrentHashMap<>();
    }

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @PostConstruct
    public void init(){
        try {
            Map<String, Job> beansOfType = beanFactory.getBeansOfType(Job.class);

            beansOfType.forEach((key,value)->{
                Class clazz = value.getClass();
                //获取注解
                AutoTaskTimer autoTaskTimer = (AutoTaskTimer) clazz.getAnnotation(AutoTaskTimer.class);
                if (autoTaskTimer != null) {
                    TimerTaskDetails timerTaskDetails  = new TimerTaskDetails();
                    timerTaskDetails.setCron(autoTaskTimer.cron());
                    timerTaskDetails.setGroup(autoTaskTimer.group().value());
                    timerTaskDetails.setStatus(0);
                    timerTaskDetails.setTimerDesc(autoTaskTimer.timerDesc());
                    timerTaskDetails.setTimerName(autoTaskTimer.timerName());
                    timerTaskDetails.setJobClass(clazz);
                    timerTaskDetailsMap.put(autoTaskTimer.timerName(),timerTaskDetails);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取定时任务异常");
            throw new RuntimeException("获取定时任务异常");
        }
    }

    /**
     * 只提供get方法，不允许修改
     * @return
     */
    public Map<String, TimerTaskDetails> getTimerTaskDetailsMap() {
        return timerTaskDetailsMap;
    }
}
