package cn.lucky.jdautotask.handle.superMarket.dailyTasks;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * 做日常任务
 */
@Log4j2
public class SuperMarketSmtgDoShopTaskRequest extends SuperMarketSmtgObtainShopTaskPrizeRequest {

    public SuperMarketSmtgDoShopTaskRequest() {
        setFunctionId("smtg_doShopTask");
    }


    public void setBody(@NonNull String taskId,@NonNull Long itemId) {
        super.taskId = taskId;
        Map<String, Object> map = new HashMap<>();
        map.put("taskId",taskId);
        map.put("channel","18");
        map.put("itemId",itemId);
        setBodyByValueObject(map);
    }
}
