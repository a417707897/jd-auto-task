package cn.lucky.jdautotask.handle.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.handle.common.JdTimerJob;
import cn.lucky.jdautotask.handle.nian.GetProduceFirecrackersHandle;
import cn.lucky.jdautotask.handle.nian.NianDailyTasksHandle;
import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@AutoTaskTimer(cron ="0 2 0/1 * * ?",status = false, timerName = "NianDailyTasksHandleTimer", timerDesc = "年兽日常任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class NianDailyTasksHandleTimer extends JdTimerJob {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jdUserInfo.getJdAutoTaskRequestMap().forEach((key,value)->{
            if (value.getCookieFailure()) {
                handle = new NianDailyTasksHandle();
                try {
                    handle.doExecute(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
