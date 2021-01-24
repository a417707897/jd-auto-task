package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 初始化农场, 可获取果树及用户信息API
 */
public class FarmInitForFarmRequest extends AbstractRequestFarmPost {

    public FarmInitForFarmRequest() {
        setFunctionId("initForFarm");
        setBodyByStr("{\"version\":4}");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
