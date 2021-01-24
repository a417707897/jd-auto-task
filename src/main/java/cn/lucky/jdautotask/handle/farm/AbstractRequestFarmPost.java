package cn.lucky.jdautotask.handle.farm;

import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import org.springframework.http.HttpMethod;

import java.util.Date;

/**
 * 京东农场post抽象
 */
public abstract class AbstractRequestFarmPost extends AbstractRequestInfoWithExAction {


    public AbstractRequestFarmPost() {
        httpMethod = HttpMethod.POST;
        httpHeaders.set("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
        httpHeaders.set("cache-control", "no-cache");
        httpHeaders.set("origin", "https://home.m.jd.com");
        httpHeaders.set("pragma", "no-cache");
        httpHeaders.set("referer", "https://home.m.jd.com/myJd/newhome.action");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-site");
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        param.set("appid", "wh5");
        param.set("clientVersion", "9.1.0");
        param.set("body", "{}");

        url = "https://api.m.jd.com/client.action";
    }



}
