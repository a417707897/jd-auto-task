package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndexGet;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 浏览频道列表
 * @Date 2021/1/18 13:41
 **/
public class PlantChannelNutrientsTaskPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public PlantChannelNutrientsTaskPBIRequest() {
        param.set("functionId", "plantChannelNutrientsTask");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;

    }

    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        Assert.notNull(body.get("channelId"), "channelId参数为空");
        Assert.notNull(body.get("channelTaskId"), "channelTaskId参数为空");
    }

    public void setBody(String channelId, String channelTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("channelId", channelId);
        map.put("channelTaskId", channelTaskId);
        setBody(map);
    }


}
