package cn.lucky.jdautotask.handle.common;


import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 抽象的请求实体，带执行动作
 * @Date 2021/1/22 10:59
 **/
@Log4j2
public abstract class AbstractRequestInfoWithExAction extends AbstractRequestInfo<String> {


    public AbstractRequestInfoWithExAction(){
        requestInterval = 2000L;
        isLink = false;
    }

    //是否链接化
    private Boolean isLink;

    //请求间隔，默认两秒
    private Long requestInterval;

    @Override
    public String execute(RestTemplate restTemplate) {
        //不能请求频繁
        try {
            Thread.sleep(requestInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.notNull(restTemplate, "restTemplate参数不能为空");
        Assert.notNull(this.url, "url参数不能为空");
        Assert.notEmpty(httpHeaders.get("Cookie"), "Cookie不能为空");
        Assert.notNull(param.get("functionId"), "functionId不能为空");
        Assert.notNull(param.get("body"), "body不能为空");
        Assert.notNull(httpMethod, "请求方式不能为空");
        try {
            this.checkParam();
            log.debug("url:【{}】,开始请求", this.url);

            //链接化
            paramLinkSet();

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
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }

    public void setBodyByValueObject(Map<String, Object> body){
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }

    public void setBodyByStr(String body){
        param.set("body", body);
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

    public void setFunctionId(String functionId){
        param.set("functionId", functionId);
    }

    /*
     * 因为有些请求需要把参数拼接到url后面，所以，需要把参数拼成，http://a.com/a=1&b=2&c=3格式
     * 同时需要注意的一点是，如果拼接的参数带{}，这个是站位符的意思，所以需要另外处理
     *  @Author zyl
     * @Description 参数链接化，默认执行的
     * @Date 2021/1/14 15:47
     * @Param []
     * @return void
     **/
    public void paramLinkSet(){
        if (isLink) {
            return;
        }
        Assert.notNull(url, "url不能为空");
        Assert.notNull(param, "参数不能为空");
        log.debug("url：{}，开始进行链式拼接",url);

        //拼接url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url);
        //获取参数所有带{}
        param.forEach((key,value)->{
            //判断第一位和最后一位
            if (value.get(0).substring(0,1).equals("{")) {
                if (value.get(0).substring(value.get(0).length()-1,value.get(0).length()).equals("}")) {
                    if (placeholderValue == null) {
                        placeholderValue = new HashMap<>();
                    }
                    placeholderValue.put(key, value.get(0));
                    //设置为占位符格式数据
                    value.set(0, "{" + key + "}");
                }
            }
            builder.queryParam(key, value);
        });

        log.debug("url:【{}】,拼接成功,拼接后的效果:【{}】", this.url, builder.build().toString());
        this.url = builder.build().toString();
        isLink = true;
    }

    /**
     * 校验请求返回是否成功业务调用
     * @param jsonNode
     * @return
     */
    public Boolean checkResponseToData(JsonNode jsonNode) {
        if (jsonNode.get("code") == null ||
                !"0".equals(jsonNode.get("code").asText()) ||
                jsonNode.get("data") == null
        ) {
            return false;
        }

        return true;
    }


    //禁止参数链接化
    public void banParamLink() {
        isLink = true;
    }

    /**
     * 设置请求间隔
     * @param requestInterval
     */
    public void setRequestInterval(@NonNull Long requestInterval) {
        this.requestInterval = requestInterval;
    }
}
