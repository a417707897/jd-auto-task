package cn.lucky.jdautotask.handle.superMarket;


import cn.lucky.jdautotask.handle.common.AbstractRequestInfo;
import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import cn.lucky.jdautotask.pojo.superMarket.BizResule;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;


/**
 * 抽象的东东超市请求对象
 */
@Log4j2
public abstract class AbstractRequestSuperMarketPost extends AbstractRequestInfoWithExAction {

    public AbstractRequestSuperMarketPost(){
        httpMethod = HttpMethod.POST;
        httpHeaders.set("Host", "api.m.jd.com");
        httpHeaders.set("Referer", "https://jdsupermarket.jd.com/game");
        httpHeaders.set("Origin", "https://jdsupermarket.jd.com");
        httpHeaders.set("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        param.set("appid", "jdsupermarket");
        param.set("clientVersion", "8.0.0");
        param.set("client", "m");
        param.set("body", "{}");
        param.set("t", String.valueOf(new Date().getTime()));
        url = "https://api.m.jd.com/api";
    }

}
