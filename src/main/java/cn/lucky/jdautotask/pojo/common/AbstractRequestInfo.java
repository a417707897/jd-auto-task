package cn.lucky.jdautotask.pojo.common;

import cn.lucky.jdautotask.pojo.enums.RequestWayType;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
/*
 * @Author zyl
 * @Description 通用的头信息
 * @Date 2021/1/8 13:53
 **/
@Data
public class AbstractRequestInfo {

    //初始化
    protected AbstractRequestInfo(){
        httpHeaders = new HttpHeaders();
        param = new LinkedMultiValueMap<>();
    }

    //请求链接
    protected String url;

    //请求头信息
    protected HttpHeaders httpHeaders;

    //请求参数
    protected MultiValueMap<String, String> param;

    //请求方式
    protected RequestWayType requestWayType;

    public HttpEntity<String> getHttpEntity(){
        HttpEntity httpEntity = new HttpEntity(httpHeaders, param);
        return httpEntity;
    }

}
