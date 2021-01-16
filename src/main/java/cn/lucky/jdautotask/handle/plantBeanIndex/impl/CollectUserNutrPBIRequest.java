package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
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
 * 偷取好友营养液
 */
@Log4j2
public class CollectUserNutrPBIRequest extends AbstractRequestPlantBeanIndex {



    public CollectUserNutrPBIRequest(){
        param.set("functionId", "collectUserNutr");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
    }




    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        Assert.notNull(body.get("paradiseUuid"), "paradiseUuid参数为空");
        Assert.notNull(body.get("roundId"), "roundId参数为空");
    }

    public void setBody(@NonNull String paradiseUuid, @NonNull String roundId){
        Map<String, String> map = new HashMap<>();
        map.put("paradiseUuid", paradiseUuid);
        map.put("roundId", roundId);
        setBody(map);
    }
}
