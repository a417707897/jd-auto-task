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
 * @Description 基础的带请求动作的抽象
 * @Date 2021/1/29 14:43
 **/
@Log4j2
public abstract class AbstractRequestInfoBasic extends AbstractRequestInfo<String> {

    public AbstractRequestInfoBasic(){
        requestInterval = 2000L;
    }


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
        Assert.notNull(httpMethod, "请求方式不能为空");
        try {
            this.checkParam();
            log.debug("url:【{}】,开始请求", this.url);
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

    protected void setBodyByStr(String body){
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

    protected void setFunctionId(String functionId){
        param.set("functionId", functionId);
    }

}
