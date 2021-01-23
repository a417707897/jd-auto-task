package cn.lucky.jdautotask.handle.superMarket.shelf;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 解锁货架
 */
public class SuperMarketSmtgShelfUnlockRequest extends AbstractRequestSuperMarketGet {



    public SuperMarketSmtgShelfUnlockRequest() {
        setFunctionId("smtg_shelfUnlock");
    }

    public void setBody(@NonNull String shopId, @NonNull String shelfId) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId",shopId);
        map.put("shelfId",shelfId);
        map.put("channel",1);
        setBodyByValueObject(map);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
