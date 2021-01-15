package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.pojo.enums.RequestWayType;
import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.JsonFormatUtil;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 获取任务及基本信息
 * @Date 2021/1/11 14:14
 **/
public class PlantBeanIndexRequest extends AbstractRequestPlantBeanIndex {

    public PlantBeanIndexRequest() {
        param.set("functionId", "plantBeanIndex");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        requestWayType = RequestWayType.POST;

        setBody(null);
    }

    @Override
    public void checkParam() {
    }



    @Override
    public void setBody(Map<String, String> body) {
        body = new HashMap<>();
        body.put("version", "9.2.4.0");
        body.put("monitor_source", "plant_app_plant_index");
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }
}
