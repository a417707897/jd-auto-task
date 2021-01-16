package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author zyl
 * @Description 获取任务及基本信息
 * @Date 2021/1/11 14:14
 **/
@Log4j2
public class PlantBeanIndexPBIRequest extends AbstractRequestPlantBeanIndex {

    public PlantBeanIndexPBIRequest() {
        param.set("functionId", "plantBeanIndex");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;

        setBody(null);
    }

    @Override
    protected void checkParam() {
    }


    @Override
    public void setBody(Map<String, String> body) {
        body = new HashMap<>();
        body.put("version", "9.2.4.0");
        body.put("monitor_source", "plant_app_plant_index");
        param.set("body", JsonFormatUtil.jsonFormatObjectToStr(body));
    }
}
