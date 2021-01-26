package cn.lucky.jdautotask.handle.nian;


import cn.lucky.jdautotask.handle.common.AbstractRequestInfo;
import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
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
public abstract class AbstractNianShouRequestPost extends AbstractRequestInfoWithExAction {

    public AbstractNianShouRequestPost(){
        //头信息
        httpHeaders.set("origin", "https://h5.m.jd.com");
        httpHeaders.set("referer", "https://h5.m.jd.com/");
        httpHeaders.set("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpMethod = HttpMethod.POST;

        url = "https://api.m.jd.com/client.action";

        //参数信息
        param.set("client", "wh5");
        param.set("clientVersion", "1.0.0");
        param.set("body", "{}");

    }
}
