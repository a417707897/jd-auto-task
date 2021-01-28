package cn.lucky.jdautotask.handle.timer;

import cn.hutool.core.date.DateUtil;
import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.handle.common.JdTimerJob;
import cn.lucky.jdautotask.handle.nian.GetProduceFirecrackersHandle;
import cn.lucky.jdautotask.handle.plantBeanIndex.PlantBeanIndexHandle;
import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 种豆得豆定时器
 */
@Component
@AutoTaskTimer(cron ="0 1 0/1 * * ?", timerName = "PlantBeanIndexHandleTimer", timerDesc = "京东种豆得豆定时任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class PlantBeanIndexHandleTimer extends JdTimerJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jdUserInfo.getJdAutoTaskRequestMap().forEach((key,value)->{
            if (value.getCookieFailure()) {
                handle = new PlantBeanIndexHandle();
                try {
                    handle.doExecute(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
