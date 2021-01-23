package cn.lucky.jdautotask.handle.superMarket.other;


import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 每日签到
 */
public class SuperMarketSmtgSignRequest extends AbstractRequestSuperMarketGet {


    public SuperMarketSmtgSignRequest() {
        setFunctionId("smtg_sign");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
