package cn.lucky.jdautotask.pojo.plantBeanIndex.impl;

import cn.lucky.jdautotask.pojo.enums.RequestWayType;
import cn.lucky.jdautotask.pojo.plantBeanIndex.AbstractRequestPlantBeanIndex;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/*
 * @Author zyl
 * @Description 获取任务及基本信息
 * @Date 2021/1/11 14:14
 **/
public class PlantBeanIndexRequest extends AbstractRequestPlantBeanIndex {

    public PlantBeanIndexRequest() {
        param.set("functionId", "plantBeanIndex");
        //设置请求实体
        param.set("body", "{\"version\":\"9.2.4.0\",\"monitor_source\":\"plant_app_plant_index\"}");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        requestWayType = RequestWayType.POST;
    }

    /*
     * @Author zyl
     * @Description 设置头信息
     * @Date 2021/1/14 17:18
     * @Param [cookie]
     * @return void
     **/
    public void setCookie(@NonNull String cookie) {
        httpHeaders.set("Cookie", cookie);
    }
}
