package cn.lucky.jdautotask.handle.nian.impl;

import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * @Author zyl
 * @Description 获取可领取优惠券的店铺，每领取一个，可得1000爆竹
 * @Date 2021/1/21 16:32
 **/
public class NianKillCouponListRequest extends AbstractNianShouRequestPost {

    public NianKillCouponListRequest(){
        setFunctionId("nian_killCouponList");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
