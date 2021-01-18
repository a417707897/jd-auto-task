package cn.lucky.jdautotask.handle.timer;

import cn.hutool.core.date.DateUtil;
import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 种豆得豆定时器
 */
@Component
@AutoTaskTimer(cron ="0/5 * * * * ? ", timerName = "cs", timerDesc = "测试定时器", group = "cs")
@Log4j2
public class PlantBeanIndexHandleTimer implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("当前时间：{}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
