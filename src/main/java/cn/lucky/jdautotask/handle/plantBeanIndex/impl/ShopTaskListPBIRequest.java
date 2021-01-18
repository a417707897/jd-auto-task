package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndexGet;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 获取日常任务店铺列表，taskType=3
 * @Date 2021/1/18 10:59
 **/
public class ShopTaskListPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public ShopTaskListPBIRequest(){
        param.set("functionId", "shopTaskList");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;
        Map<String, String> body = new HashMap<>();
        body.put("monitor_refer", "plant_receiveNutrients");
        body.put("monitor_source", "plant_app_plant_index");
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
