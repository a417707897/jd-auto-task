package cn.lucky.jdautotask.handle.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.handle.common.JdTimerJob;
import cn.lucky.jdautotask.handle.farm.FarmDailyTasksHandle;
import cn.lucky.jdautotask.handle.nian.GetProduceFirecrackersHandle;
import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 东东农场日常任务
 */
@Component
@AutoTaskTimer(cron ="0 1 5,6,7,8,14,15,20 * * ? ", timerName = "FarmDailyTasksHandleTimer", timerDesc = "京东农场自动任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class FarmDailyTasksHandleTimer extends JdTimerJob {


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jdUserInfo.getJdAutoTaskRequestMap().forEach((key,value)->{
            try {
                if (value.getCookieFailure()) {
                    handle = new FarmDailyTasksHandle();
                    handle.doExecute(value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
