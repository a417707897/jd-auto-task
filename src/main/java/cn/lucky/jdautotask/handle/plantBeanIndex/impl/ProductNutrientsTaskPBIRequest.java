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
 * @Description 执行浏览商品的任务
 * @Date 2021/1/18 11:35
 **/
public class ProductNutrientsTaskPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public ProductNutrientsTaskPBIRequest(){

        param.set("functionId", "productNutrientsTask");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;
    }

    public void setBody(@NonNull String productTaskId, @NonNull String skuId) {
        Map<String, String> map = new HashMap<>();

        map.put("productTaskId", productTaskId);
        map.put("skuId", skuId);
        map.put("monitor_refer", "plant_productNutrientsTask");
        map.put("monitor_source", "plant_app_plant_index");
        setBody(map);
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        Assert.notNull(body.get("productTaskId"), "productTaskId参数为空");
        Assert.notNull(body.get("skuId"), "skuId参数为空");
    }
}
