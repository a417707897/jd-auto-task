package cn.lucky.jdautotask.handle.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketDailyTasksHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketTimingHandle;
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
@AutoTaskTimer(cron ="0 8 0/1 * * ?", timerName = "SuperMarketTimingHandleTimer", timerDesc = "东东超市每天需要定时处理的任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class SuperMarketTimingHandleTimer implements Job {


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        SuperMarketTimingHandle handle = null;
        List<String> cookies = Arrays.asList("pt_key=AAJf5w7aADAy8wXy7TrGhb6ico1jELCHQJTC7npw114j2KN2VB3EblC4297Hk5PKX433RhGRHDc;pt_pin=18337656372_p",
                "pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ");

        for (String cookie : cookies) {
            handle = new SuperMarketTimingHandle();
            JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                    .builder()
                    .cookie(cookie)
                    .build();
            handle.doExecute(jdAutoTaskRequest);
        }

    }
}
