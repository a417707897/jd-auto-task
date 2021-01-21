package cn.lucky.jdautotask.handle.nian.impl;

import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 领取店铺优惠卷，每个店铺可领1000爆竹
 * @Date 2021/1/21 16:44
 **/
public class NianKillCouponRequest extends AbstractNianShouRequestPost {

    public NianKillCouponRequest(){
        //参数信息
        param.set("functionId", "nian_killCoupon");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
    }


    @Override
    protected void checkParam() throws JsonProcessingException {

    }

    public void setBody(String secretp,Long skuId) throws JsonProcessingException {
        AssertUtil.strNotNull(secretp, "secretp不能为空");
        Assert.notNull(skuId, "skuId不能为空");

        Map<String, Object> extraData = new HashMap<>();
        extraData.put("jj", 6);
        extraData.put("buttonid", "jmdd-react-smash_0");
        extraData.put("sceneid", "homePageh5");
        extraData.put("appid", "50073");

        Map<String, Object> businessData = new HashMap<>();
        businessData.put("skuId", skuId);
        businessData.put("rnd", (int)(Math.random() * 1000000));
        businessData.put("inviteId", "-1");
        businessData.put("stealId", "-1");

        Map<String, Object> temp = new HashMap<>();
        temp.put("secretp", secretp);
        temp.put("extraData", JsonFormatUtil.getObjectMapper().writeValueAsString(extraData));
        temp.put("businessData", JsonFormatUtil.getObjectMapper().writeValueAsString(businessData));

        Map<String, Object> bodyTemp = new HashMap<>();
        bodyTemp.put("ss", temp);
        bodyTemp.put("skuId", skuId);
        setBodyByStr(JsonFormatUtil.jsonFormatObjectToStr(bodyTemp));
    }

}
