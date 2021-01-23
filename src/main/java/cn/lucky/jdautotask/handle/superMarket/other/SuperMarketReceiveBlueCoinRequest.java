package cn.lucky.jdautotask.handle.superMarket.other;


import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

/**
 * 收取小费
 */
@Log4j2
public class SuperMarketReceiveBlueCoinRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketReceiveBlueCoinRequest() {
        setFunctionId("smtg_receiveCoin");
        setBodyByStr("{\"type\":2,\"channel\":\"18\"}");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
