package cn.lucky.jdautotask.handle.nian.dailyTasks;


import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 查询日常任务详情
 */
public class NianGetTaskDetailRequest extends AbstractNianShouRequestPost {

    public NianGetTaskDetailRequest() {
        setFunctionId("nian_getTaskDetail");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
