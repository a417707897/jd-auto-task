package cn.lucky.jdautotask.handle.nian.impl;

import cn.hutool.core.lang.Assert;
import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 收取已经产生的爆竹
 * @Date 2021/1/21 13:37
 **/
public class NianCollectProduceScoreRequest extends AbstractNianShouRequestPost {

    public NianCollectProduceScoreRequest(){
        //参数信息
        param.set("functionId", "nian_collectProduceScore");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
    }


    @Override
    protected void checkParam() throws JsonProcessingException {

    }

    public void setBody(String secretp) throws JsonProcessingException {
        AssertUtil.strNotNull(secretp, "secretp");

        Map<String, Object> extraData = new HashMap<>();
        extraData.put("jj", 6);
        extraData.put("buttonid", "jmdd-react-smash_0");
        extraData.put("sceneid", "homePageh5");
        extraData.put("appid", "50073");

        Map<String, Object> businessData = new HashMap<>();
        businessData.put("taskId", "collectProducedCoin");
        businessData.put("rnd", (int)(Math.random() * 1000000));
        businessData.put("inviteId", "-1");
        businessData.put("stealId", "-1");

        Map<String, String> temp = new HashMap<>();
        temp.put("secretp", secretp);
        temp.put("extraData", JsonFormatUtil.getObjectMapper().writeValueAsString(extraData));
        temp.put("businessData", JsonFormatUtil.getObjectMapper().writeValueAsString(businessData));

        Map<String, Map<String, String>> bodyTemp = new HashMap<>();
        bodyTemp.put("ss", temp);
        setBodyByStr(JsonFormatUtil.jsonFormatObjectToStr(bodyTemp));
    }

}
