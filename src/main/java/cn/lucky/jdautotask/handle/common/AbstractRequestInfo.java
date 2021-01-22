package cn.lucky.jdautotask.handle.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.NonNull;
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
        placeholderValue = new HashMap<>(); }

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


    protected abstract void checkParam() throws JsonProcessingException;

    public abstract T execute(RestTemplate restTemplate) throws InterruptedException;

}
