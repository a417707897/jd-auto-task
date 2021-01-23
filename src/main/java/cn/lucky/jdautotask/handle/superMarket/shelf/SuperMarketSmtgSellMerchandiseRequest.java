package cn.lucky.jdautotask.handle.superMarket.shelf;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 售卖限时物品
 */
public class SuperMarketSmtgSellMerchandiseRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgSellMerchandiseRequest() {
        setFunctionId("smtg_sellMerchandise");
    }

    public void setBody(@NonNull String shopId, @NonNull String merchandiseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId",shopId);
        map.put("merchandiseId",merchandiseId);
        map.put("channel",18);
        setBodyByValueObject(map);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
