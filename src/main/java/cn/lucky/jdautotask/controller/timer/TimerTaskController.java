package cn.lucky.jdautotask.controller.timer;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/*
 * @Author zyl
 * @Description 定时器管理类
 * @Date 2021/1/18 17:24
 **/
@RestController
@RequestMapping("/timer/task")
@Validated
public class TimerTaskController {


    @GetMapping("/start")
    public String start(@NotNull(message = "taskName不能为空") String taskName) {

        return null;
    }

    @GetMapping("/stop")
    public String stop(){

        return null;
    }


}
