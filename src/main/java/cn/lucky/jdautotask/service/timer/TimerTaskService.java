package cn.lucky.jdautotask.service.timer;

import cn.lucky.jdautotask.handle.common.Result;
import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
import org.quartz.SchedulerException;

import java.text.ParseException;

public interface TimerTaskService {

    Result start(TimerTaskDetails timerTaskDetails);

    Result stop(String taskName);
}
