//package cn.lucky.jdautotask.handle.timer;
//
//import cn.lucky.jdautotask.config.annotions.timer.AutoTaskTimer;
//import cn.lucky.jdautotask.config.timer.ScanTimerTaskConfig;
//import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
//import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
//import lombok.SneakyThrows;
//import lombok.extern.log4j.Log4j2;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@AutoTaskTimer(cron ="0/6 * * * * ?", timerName = "TimerTest", timerDesc = "测试定时器", group = TimerGroupType.JD_AUTO_TASK)
//@Log4j2
//public class TimerTest implements Job {
//
//    @Autowired
//    private ScanTimerTaskConfig scanTimerTaskConfig;
//
//    @SneakyThrows
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("测试定时器执行了");
//        Thread.sleep(3000);
//        TimerTaskDetails timerTest = scanTimerTaskConfig.getTimerTaskDetailsMap().get("TimerTest");
//        timerTest.setIsRunning(0);
//
//    }
//}
