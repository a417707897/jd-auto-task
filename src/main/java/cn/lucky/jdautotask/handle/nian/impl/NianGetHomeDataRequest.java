package cn.lucky.jdautotask.handle.nian.impl;

import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

/*
 * @Author zyl
 * @Description 获取年兽活动页首页数据
 * @Date 2021/1/21 10:45
 **/

@Log4j2
public class NianGetHomeDataRequest extends AbstractNianShouRequestPost {


    public NianGetHomeDataRequest() {
        //参数信息
        param.set("functionId", "nian_getHomeData");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}




