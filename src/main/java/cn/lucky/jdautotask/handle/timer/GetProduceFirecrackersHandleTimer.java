package cn.lucky.jdautotask.handle.timer;

import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
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
import java.util.List;

/**
 * 定时领取爆竹并且炸年兽
 */
@Component
@AutoTaskTimer(cron ="0 1 0/1 * * ?", timerName = "GetProduceFirecrackersHandleTimer", timerDesc = "京东自动领取爆竹，每小时自动领取", group = TimerGroupType.JD_AUTO_TASK)
@Log4j2
public class GetProduceFirecrackersHandleTimer implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        GetProduceFirecrackersHandle getProduceFirecrackersHandle = null;
        List<String> cookies = Arrays.asList("pt_key=AAJf5w7aADAy8wXy7TrGhb6ico1jELCHQJTC7npw114j2KN2VB3EblC4297Hk5PKX433RhGRHDc;pt_pin=18337656372_p",
                "pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ");

        for (String cookie : cookies) {
            getProduceFirecrackersHandle = new GetProduceFirecrackersHandle();
            JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                    .builder()
                    .cookie(cookie)
                    .build();

            getProduceFirecrackersHandle.doExecute(jdAutoTaskRequest);
        }

    }
}
