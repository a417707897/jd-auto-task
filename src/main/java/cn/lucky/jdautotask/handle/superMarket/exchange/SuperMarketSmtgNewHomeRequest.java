package cn.lucky.jdautotask.handle.superMarket.exchange;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 东东超市新首页信息
 */
public class SuperMarketSmtgNewHomeRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgNewHomeRequest() {
        setFunctionId("smtg_newHome");
        setBodyByStr("{\"channel\": \"18\" }");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
