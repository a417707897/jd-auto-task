package cn.lucky.jdautotask.handle.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.handle.common.JdTimerJob;
import cn.lucky.jdautotask.handle.plantBeanIndex.PlantBeanIndexHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketDailyTasksHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketExchangeHandle;
import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
@AutoTaskTimer(cron ="0 0 8 * * ?", timerName = "SuperMarketDailyTasksHandleTimer", timerDesc = "东东超市每日任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class SuperMarketDailyTasksHandleTimer extends JdTimerJob {


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        jdUserInfo.getJdAutoTaskRequestMap().forEach((key,value)->{
            if (value.getCookieFailure()) {
                handle = new SuperMarketDailyTasksHandle();
                try {
                    handle.doExecute(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
