package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
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
 * @Description 领取上轮京东奖励
 * @Date 2021/1/20 10:46
 **/
public class ReceivedBeanPBIRequest extends AbstractRequestPlantBeanIndex {

    public ReceivedBeanPBIRequest(){
        param.set("functionId", "receivedBean");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(this.getParam().get("body").get(0));
        Assert.notNull(body.get("roundId"),"roundId不能为空");
    }

    public void setBody(String lastRoundId){
        Map<String, String> map = new HashMap<>();
        map.put("roundId", lastRoundId);
        map.put("monitor_source", "plant_app_plant_index");
        setBody(map);
    }

}
