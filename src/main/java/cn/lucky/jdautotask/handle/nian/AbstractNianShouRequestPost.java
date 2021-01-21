package cn.lucky.jdautotask.handle.nian;


import cn.lucky.jdautotask.handle.common.AbstractRequestInfo;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/*
 * @Author zyl
 * @Description 抽象的年兽请求实体
 * @Date 2021/1/21 10:48
 **/
@Log4j2
public abstract class AbstractNianShouRequestPost extends AbstractRequestInfo<String> {

    public AbstractNianShouRequestPost(){
        //头信息
        httpHeaders.add("origin", "https://h5.m.jd.com");
        httpHeaders.add("referer", "https://h5.m.jd.com/");
        httpHeaders.add("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpMethod = HttpMethod.POST;

        //参数信息
        param.set("client", "wh5");
        param.set("clientVersion", "1.0.0");
    }


    @Override
    public String execute(RestTemplate restTemplate) throws InterruptedException {
        //不能请求频繁
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.notNull(restTemplate, "restTemplate参数不能为空");
        Assert.notNull(this.url, "url参数不能为空");
        Assert.notEmpty(httpHeaders.get("Cookie"), "Cookie不能为空");
        Assert.notEmpty(param.get("functionId"), "functionId不能为空");
        Assert.notNull(httpMethod, "请求方式不能为空");
        try {
            this.checkParam();
            log.debug("url:【{}】,开始请求", this.url);
            //链接化
            super.paramLinkSet();

            ResponseEntity<String> exchange = restTemplate.exchange(this.url,
                    httpMethod,
                    super.getHttpEntityOnlyHeaders(),
                    String.class,
                    super.getPlaceholderValue());

            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求出现异常");
        }
    }

    public void setBody(Map<String, String> body){
        param.remove("body");
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }

    public void setBodyByStr(String body){
        param.remove("body");
        param.set("body", body);
    }

}
