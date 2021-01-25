package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * @Author zyl
 * @Description 小鸭子游戏
 * @Date 2021/1/25 15:21
 **/
public class FarmGetFullCollectionRewardRequest extends AbstractRequestFarmPost {

    public FarmGetFullCollectionRewardRequest() {
        setFunctionId("getFullCollectionReward");
        setBodyByStr("{\"type\": 2, \"version\": 6, \"channel\": 2}");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
