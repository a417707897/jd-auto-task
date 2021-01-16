package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 领取自己的营养液
 */
@Log4j2
public class ReceiveNutrientsPBIRequest extends AbstractRequestPlantBeanIndex {

    public ReceiveNutrientsPBIRequest(){
        param.set("functionId", "receiveNutrients");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
    }

    /**
     * 校验roundId值是否为空
     */
    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
            JsonNode body = objectMapper.readTree(this.param.get("body").get(0));
        JsonNode roundId = body.get("roundId");
        Assert.notNull(roundId, "roundId不能为空");
    }

    public void setBody(@NonNull String roundId) {
        Map<String, String> map = new HashMap<>();
        map.put("roundId", roundId);
        map.put("monitor_refer", "plant_receiveNutrients");
        setBody(map);
    }


}
