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

/**
 * 接手营养液任务
 */
public class ReceiveNutrientsTaskPBIRequest extends AbstractRequestPlantBeanIndexGet {

    public ReceiveNutrientsTaskPBIRequest(){
        param.set("functionId", "receiveNutrientsTask");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.GET;
    }

    public void setBody(@NonNull String awardType) {
        Map<String, String> map = new HashMap<>();
        map.put("awardType", awardType);
        map.put("monitor_refer", "receiveNutrientsTask");
        map.put("monitor_source", "plant_app_plant_index");
        setBody(map);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        Assert.notNull(body.get("awardType"), "awardType不能为空");
    }
}
