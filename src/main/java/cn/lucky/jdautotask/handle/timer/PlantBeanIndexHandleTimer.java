package cn.lucky.jdautotask.handle.timer;

import cn.hutool.core.date.DateUtil;
import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
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
@AutoTaskTimer(cron ="0 10 0/3 * * ?", timerName = "PlantBeanIndexHandleTimer", timerDesc = "京东种豆得豆定时任务", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class PlantBeanIndexHandleTimer implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        PlantBeanIndexHandle plantBeanIndexHandle = null;
        List<String> cookies = Arrays.asList("pt_key=AAJf5w7aADAy8wXy7TrGhb6ico1jELCHQJTC7npw114j2KN2VB3EblC4297Hk5PKX433RhGRHDc;pt_pin=18337656372_p",
                "pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ");
        for (String cookie : cookies) {
            plantBeanIndexHandle = new PlantBeanIndexHandle();
            JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                    .builder()
                    .cookie(cookie)
                    .build();
            plantBeanIndexHandle.doExecute(jdAutoTaskRequest);
        }
    }
}
