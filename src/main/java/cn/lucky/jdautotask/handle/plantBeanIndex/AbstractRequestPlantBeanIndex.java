package cn.lucky.jdautotask.handle.plantBeanIndex;

import cn.lucky.jdautotask.handle.common.AbstractRequestInfo;
import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/*
 * @Author zyl
 * @Description 抽象出种豆得豆配置
 * @Date 2021/1/11 14:13
 **/
@Log4j2
public abstract class AbstractRequestPlantBeanIndex extends AbstractRequestInfoWithExAction {

    //设置通用的头信息
    protected AbstractRequestPlantBeanIndex() {
        //头信息
        httpHeaders.add("Host", "api.m.jd.com");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.add("Accept-Language", "zh-Hans-CN;q=1,en-CN;q=0.9");
        httpHeaders.add("Accept-Encoding", "gzip, deflate, br");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        //参数信息
        param.set("appid", "ld");
        param.set("client", "apple");
        param.set("area", "19_1601_50258_51885");
        param.set("build", "167490");
        param.set("clientVersion", "9.3.2");
    }
}
