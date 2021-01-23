package cn.lucky.jdautotask.handle.superMarket.other;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 每天从指定入口进入游戏,可获得额外奖励
 */
public class SuperMarketBeanSignRequest extends SuperMarketSmtgSignRequest {

    public SuperMarketBeanSignRequest() {
        setBodyByStr("{\"channel\": \"1\"}");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
