package cn.lucky.jdautotask.handle.superMarket.dailyTasks;

import cn.hutool.core.util.StrUtil;
import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import cn.lucky.jdautotask.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * 领取已经做完日常任务的奖励
 */
@Log4j2
public class SuperMarketSmtgObtainShopTaskPrizeRequest extends AbstractRequestSuperMarketGet {

    protected String taskId;

    public SuperMarketSmtgObtainShopTaskPrizeRequest() {
        setFunctionId("smtg_obtainShopTaskPrize");
    }

    public void setBody(@NonNull String taskId) {
        this.taskId = taskId;
        Map<String, Object> map = new HashMap<>();
        map.put("taskId",taskId);
        map.put("channel","18");
        setBodyByValueObject(map);
    }



    @Override
    protected void checkParam() throws JsonProcessingException {
        if (StrUtil.isBlank(taskId)) {
            log.info("taskId不能为空,请您设置body");
            return;
        }
    }
}
