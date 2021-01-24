package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 定时领水API
 */
public class FarmGotThreeMealForFarmRequest extends AbstractRequestFarmGet {
    public FarmGotThreeMealForFarmRequest() {
        setFunctionId("gotThreeMealForFarm");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
