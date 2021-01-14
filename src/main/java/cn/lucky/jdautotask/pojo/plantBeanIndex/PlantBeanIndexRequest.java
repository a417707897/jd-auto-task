package cn.lucky.jdautotask.pojo.plantBeanIndex;

import cn.lucky.jdautotask.pojo.enums.RequestWayType;

/*
 * @Author zyl
 * @Description 获取任务及基本信息
 * @Date 2021/1/11 14:14
 **/
public class PlantBeanIndexRequest extends AbstractRequestPlantBeanIndex {


    public PlantBeanIndexRequest(){
        //设置头信息cookie
        httpHeaders.set("Cookie","pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ");

        param.set("functionId","plantBeanIndex");
        //设置请求实体
        param.set("body", "{bodyValue}");
        param.set("bodyValue","{\"version\":\"9.2.4.0\",\"monitor_source\":\"plant_app_plant_index\"}");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        requestWayType = RequestWayType.POST;
    }
}
