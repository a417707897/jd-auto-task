package cn.lucky.jdautotask.handle.superMarket;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpMethod;

/**
 * 东东超市Get请求
 */
public abstract class AbstractRequestSuperMarketGet extends AbstractRequestSuperMarketPost {

    public AbstractRequestSuperMarketGet() {
        httpMethod = HttpMethod.GET;
    }


}
