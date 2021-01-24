package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 领取被水滴砸中的水滴
 */
public class FarmGotWaterGoalTaskForFarmRequest extends AbstractRequestFarmGet {

    public FarmGotWaterGoalTaskForFarmRequest() {
        setFunctionId("gotWaterGoalTaskForFarm");
        setBodyByStr("{\"type\": 3}");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
