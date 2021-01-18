package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndexGet;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 获取商品列表
 * @Date 2021/1/18 11:33
 **/
public class ProductTaskListPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public ProductTaskListPBIRequest(){
        param.set("functionId", "productTaskList");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;
        Map<String, String> body = new HashMap<>();
        body.put("monitor_refer", "plant_productTaskList");
        body.put("monitor_source", "plant_app_plant_index");
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
