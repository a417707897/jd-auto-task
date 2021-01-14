package cn.lucky.jdautotask.pojo.plantBeanIndex;

import cn.lucky.jdautotask.pojo.common.AbstractRequestInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/*
 * @Author zyl
 * @Description 抽象出种豆得豆配置
 * @Date 2021/1/11 14:13
 **/
@Slf4j
public abstract class AbstractRequestPlantBeanIndex extends AbstractRequestInfo<String> {

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

    @Override
    public String execute(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "restTemplate参数不能为空");
        Assert.notNull(this.url, "url参数不能为空");
        Assert.notEmpty(httpHeaders.get("Cookie"), "Cookie不能为空");
        Assert.notEmpty(param.get("functionId"), "functionId不能为空");
        Assert.notEmpty(param.get("body"), "body不能为空");
        try {
            this.checkParam();
            log.debug("url:【{}】,开始请求", this.url);
            //链接化
            super.paramLinkSet();

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(this.url,
                    super.getHttpEntityOnlyHeaders(),
                    String.class,
                    super.getPlaceholderValue()
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求出现异常");
        }
    }

    /*
     * @Author zyl
     * @Description 设置Cookie
     * @Date 2021/1/14 17:18
     * @Param [cookie]
     * @return void
     **/
    public void setCookie(@NonNull String cookie) {
        httpHeaders.set("Cookie", cookie);
    }

    public abstract void checkParam() throws JsonProcessingException;
}
