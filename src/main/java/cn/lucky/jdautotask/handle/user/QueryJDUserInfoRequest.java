package cn.lucky.jdautotask.handle.user;

import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/*
 * @Author zyl
 * @Description JD用户请求
 * @Date 2021/1/27 13:51
 **/
public class QueryJDUserInfoRequest extends AbstractRequestInfoWithExAction {

    public QueryJDUserInfoRequest() {
        httpMethod = HttpMethod.POST;
        url = "https://wq.jd.com/user/info/QueryJDUserInfo?sceneval=2";
        httpHeaders.set("Accept", "application/json,text/plain, */*");
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.set("Accept-Encoding", "gzip, deflate, br");
        httpHeaders.set("Accept-Language", "zh-cn");
        httpHeaders.set("Referer", "https://wqs.jd.com/my/jingdou/my.shtml?sceneval=2");
        httpHeaders.set("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");

    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }

    @Override
    public String execute(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "请求实体不能为空");

        ResponseEntity<String> exchange = restTemplate.exchange(this.url,
                httpMethod,
                super.getHttpEntity(),
                String.class,
                super.getPlaceholderValue());

        return exchange.getBody();
    }
}
