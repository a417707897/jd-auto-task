package cn.lucky.jdautotask.handle.farm.dailyTasks;


import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 东东农场签到
 */
public class FarmSignForFarmRequest extends AbstractRequestFarmGet {

    public FarmSignForFarmRequest() {
        setFunctionId("signForFarm");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
