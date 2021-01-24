package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 初始化东东农场任务列表
 */
public class FarmTaskInitForFarmRequest extends AbstractRequestFarmGet {

    public FarmTaskInitForFarmRequest() {
        setFunctionId("taskInitForFarm");
    }



    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
