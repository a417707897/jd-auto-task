package cn.lucky.jdautotask.handle.farm.dailyTasks;

import cn.lucky.jdautotask.handle.farm.AbstractRequestFarmGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览广告任务API
 * type为0时, 完成浏览任务
 * type为1时, 领取浏览任务奖励
 */
public class FarmBrowseAdTaskForFarmRequest extends AbstractRequestFarmGet {

    public FarmBrowseAdTaskForFarmRequest() {
        setFunctionId("browseAdTaskForFarm");
    }

    /**
     * 浏览
     * @param advertId
     */
    public void setBrowseBody(@NonNull String advertId) {
        Map<String, Object> map = new HashMap<>();
        map.put("advertId", advertId);
        map.put("type", 0);
        setBodyByValueObject(map);
    }

    /**
     * 领取
     * @param advertId
     */
    public void setReceiveBody(@NonNull String advertId) {
        Map<String, Object> map = new HashMap<>();
        map.put("advertId", advertId);
        map.put("type", 1);
        setBodyByValueObject(map);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
