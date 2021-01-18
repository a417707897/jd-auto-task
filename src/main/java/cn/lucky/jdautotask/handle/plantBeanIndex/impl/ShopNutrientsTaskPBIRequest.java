package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndexGet;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 浏览商铺任务
 * @Date 2021/1/18 11:06
 **/
public class ShopNutrientsTaskPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public ShopNutrientsTaskPBIRequest(){
        param.set("functionId", "shopNutrientsTask");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        Assert.notNull(body.get("shopId"), "shopId参数为空");
        Assert.notNull(body.get("shopTaskId"), "shopTaskId参数为空");
    }

    public void setBody(@NonNull String shopId, @NonNull String shopTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("monitor_refer", "plant_shopNutrientsTask");
        map.put("monitor_source", "plant_app_plant_index");
        map.put("shopId", shopId);
        map.put("shopTaskId", shopTaskId);
        setBody(map);
    }
}
