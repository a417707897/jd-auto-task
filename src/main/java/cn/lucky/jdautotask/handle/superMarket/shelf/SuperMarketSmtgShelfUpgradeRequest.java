package cn.lucky.jdautotask.handle.superMarket.shelf;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketGet;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 升级货架
 */
public class SuperMarketSmtgShelfUpgradeRequest extends AbstractRequestSuperMarketGet {

    public SuperMarketSmtgShelfUpgradeRequest() {
        setFunctionId("smtg_shelfUpgrade");
    }

    public void setBody(@NonNull String shopId, @NonNull String shelfId,@NonNull Integer level) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId",shopId);
        map.put("shelfId",shelfId);
        map.put("channel",1);
        map.put("targetLevel", level + 1);
        setBodyByValueObject(map);
    }
    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
