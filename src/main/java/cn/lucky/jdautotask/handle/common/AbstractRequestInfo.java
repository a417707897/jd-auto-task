package cn.lucky.jdautotask.handle.common;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 通用的请求实体
 * @Date 2021/1/8 13:53
 **/
@Data
@Log4j2
public abstract class AbstractRequestInfo<T> {

    //初始化
    protected AbstractRequestInfo(){
        httpHeaders = new HttpHeaders();
        param = new LinkedMultiValueMap<>();
        placeholderValue = new HashMap<>();
    }

    //请求链接
    protected String url;

    //请求头信息
    protected HttpHeaders httpHeaders;

    //请求参数
    protected MultiValueMap<String, String> param;

    //请求方式
    protected HttpMethod httpMethod;

    //占位符value
    protected Map<String,String> placeholderValue;

    /*
     * @Author zyl
     * @Description 获取http请求实体，但是只有
     * @Date 2021/1/14 15:36
     * @Param []
     * @return org.springframework.http.HttpEntity<java.lang.String>
     **/
    public HttpEntity<String> getHttpEntityOnlyHeaders(){
        Assert.notNull(this.httpHeaders, "头信息不能为空");
        return getHttpEntity(this.httpHeaders, null);
    }

    /*
     * @Author zyl
     * @Description 获取请求实体
     * @Date 2021/1/14 15:39
     * @Param []
     * @return org.springframework.http.HttpEntity<java.lang.String>
     **/
    public HttpEntity<String> getHttpEntity(){
        Assert.notNull(this.httpHeaders, "头信息不能为空");
        Assert.notNull(this.param, "参数不能为空");
        return getHttpEntity(this.httpHeaders, param);
    }

    public HttpEntity<String> getHttpEntity(HttpHeaders httpHeaders,MultiValueMap<String, String> param){
        HttpEntity httpEntity = new HttpEntity(param,httpHeaders);
        return httpEntity;
    }

    /*
     * 因为有些请求需要把参数拼接到url后面，所以，需要把参数拼成，http://a.com/a=1&b=2&c=3格式
     * 同时需要注意的一点是，如果拼接的参数带{}，这个是站位符的意思，所以需要另外处理
     *  @Author zyl
     * @Description 参数链接化
     * @Date 2021/1/14 15:47
     * @Param []
     * @return void
     **/
    public void paramLinkSet(){
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
    }

    public abstract T execute(RestTemplate restTemplate) throws InterruptedException;

}
