package cn.lucky.jdautotask.handle.superMarket.other;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 领取店铺的蓝币奖励
 */
public class  SuperMarketSmtgReceiveCoinRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgReceiveCoinRequest() {
        setFunctionId("smtg_receiveCoin");
    }

    /**
     * 领取升级店铺蓝币奖励
     * @param id
     */
    public void setBodyByReceiveUpgrade(@NonNull String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("type", 5);
        setBodyByValueObject(map);
    }



    /**
     * 领取营业额
     */
    public void setBodyByReceiveTurnover() {
        Map<String, Object> map = new HashMap<>();
        map.put("channel", "18");
        map.put("type", 4);
        setBodyByValueObject(map);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
