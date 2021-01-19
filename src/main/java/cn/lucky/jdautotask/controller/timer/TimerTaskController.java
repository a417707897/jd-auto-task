package cn.lucky.jdautotask.controller.timer;

import cn.lucky.jdautotask.config.timer.ScanTimerTaskConfig;
import cn.lucky.jdautotask.handle.common.Result;
import cn.lucky.jdautotask.pojo.timer.TimerTaskDetails;
import cn.lucky.jdautotask.service.timer.TimerTaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.*;

/*
 * @Author zyl
 * @Description 定时器管理类
 * @Date 2021/1/18 17:24
 **/
@RestController
@RequestMapping("/timer/task")
@Validated
@Log4j2
public class TimerTaskController {

    @Autowired
    private TimerTaskService timerTaskService;

    @Autowired
    private ScanTimerTaskConfig scanTimerTaskConfig;

    private Map<String,TimerTaskDetails> timerTaskDetailsMap;

    /**
     * 开启定时任务
     * @param taskName
     * @return
     */
    @GetMapping("/start")
    public Result start(@NotNull(message = "taskName不能为空") String taskName) {

        TimerTaskDetails timerTaskDetails = timerTaskDetailsMap.get(taskName);
        if (timerTaskDetails == null) {
            return Result.fail("无此定时器");
        }
        return timerTaskService.start(timerTaskDetails);
    }

    @GetMapping("/stop")
    public Result stop(@NotNull(message = "taskName不能为空") String taskName){

        return timerTaskService.stop(taskName);
    }

    @GetMapping("/updateCron")
    public Result updateCron(@NotNull(message = "taskName不能为空") @RequestParam("taskName") String taskName,
                             @NotNull(message = "cron不能为空") @RequestParam("cron") String cron){
        try {
            TimerTaskDetails timerTaskDetails = timerTaskDetailsMap.get(taskName);
            if (timerTaskDetails == null) {
                return Result.fail("无此定时器");
            }
            stop(taskName);
            timerTaskDetails.setCron(cron);
            start(taskName);
            return Result.success("修改定时器成功");
        } catch (Exception e) {
            return Result.fail("修改定时器异常");
        }
    }

    /**
     * 获取可执行定时任务详情
     * @return
     */
    @GetMapping("/jobDetails")
    public Result getJobDetails(){
        return Result.success().add("items", timerTaskDetailsMap);
    }

    /**
     * 初始化定时任务
     */
    @PostConstruct
    public void init(){
        timerTaskDetailsMap = scanTimerTaskConfig.getTimerTaskDetailsMap();
        timerTaskDetailsMap.forEach((key,value)->{
            start(key);
        });
    }
}
