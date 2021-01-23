package cn.lucky.jdautotask.handle.superMarket.shelf;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 获取货架基础信息
 */
public class SuperMarketSmtgShopIndexRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgShopIndexRequest() {
        setFunctionId("smtg_shopIndex");
        setBodyByStr("{ \"channel\": 1 }");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
