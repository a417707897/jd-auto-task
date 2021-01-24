package cn.lucky.jdautotask.handle.farm;

import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import org.springframework.http.HttpMethod;

/**
 * 东东农场get抽象
 */
public abstract class AbstractRequestFarmGet extends AbstractRequestInfoWithExAction {

    public AbstractRequestFarmGet() {
        url = "https://api.m.jd.com/client.action";
        httpMethod = HttpMethod.GET;
        httpHeaders.set("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");

        param.set("appid", "wh5");
        param.set("clientVersion", "9.1.0");
        param.set("body", "{}");
    }


}
