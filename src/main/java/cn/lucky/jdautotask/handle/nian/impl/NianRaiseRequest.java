package cn.lucky.jdautotask.handle.nian.impl;

import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 炸年兽升级接口
 * @Date 2021/1/21 16:00
 **/
public class NianRaiseRequest extends NianCollectProduceScoreRequest {

    public NianRaiseRequest(){
        //参数信息
        setFunctionId("nian_raise");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
    }
    @Override
    protected void checkParam() throws JsonProcessingException {
    }

}
