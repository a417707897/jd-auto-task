package cn.lucky.jdautotask.handle.superMarket.dailyTasks;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 获取东东超市日常任务列表
 */
public class SuperMarketSmtgQueryShopTaskRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgQueryShopTaskRequest() {
        setFunctionId("smtg_queryShopTask");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
