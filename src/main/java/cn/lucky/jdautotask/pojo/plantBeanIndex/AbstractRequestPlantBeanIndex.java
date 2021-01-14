package cn.lucky.jdautotask.pojo.plantBeanIndex;

import cn.lucky.jdautotask.pojo.common.AbstractRequestInfo;
import org.springframework.http.MediaType;

/*
 * @Author zyl
 * @Description 抽象出种豆得豆配置
 * @Date 2021/1/11 14:13
 **/
public abstract class AbstractRequestPlantBeanIndex extends AbstractRequestInfo {

    //设置通用的头信息
    protected AbstractRequestPlantBeanIndex(){
        //头信息
        httpHeaders.add("Host", "api.m.jd.com");
        httpHeaders.add("Connection", "keep-alive");
        httpHeaders.add("User-Agent", "jdapp;iPhone;9.2.2;14.2;%E4%BA%AC%E4%B8%9C/9.2.2 CFNetwork/1206 Darwin/20.1.0");
        httpHeaders.add("Accept-Language", "zh-Hans-CN;q=1,en-CN;q=0.9");
        httpHeaders.add("Accept-Encoding", "gzip, deflate, br");
//        httpHeaders.add("Content-Type","application/x-www-form-urlencoded");
        //参数信息
        param.set("appid", "ld");
        param.set("client", "apple");
        param.set("area", "19_1601_50258_51885");
        param.set("build", "167490");
        param.set("clientVersion", "9.3.2");
    }

}
