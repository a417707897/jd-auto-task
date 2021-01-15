package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.pojo.enums.RequestWayType;
import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Map;

/**
 * 领取自己的营养液
 */
@Slf4j
public class ReceiveNutrientsPBIRequest extends AbstractRequestPlantBeanIndex {

    public ReceiveNutrientsPBIRequest(){
        param.set("functionId", "receiveNutrients");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        requestWayType = RequestWayType.POST;
    }

    /**
     * 校验roundId值是否为空
     */
    @Override
    public void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
            JsonNode body = objectMapper.readTree(this.param.get("body").get(0));
        JsonNode roundId = body.get("roundId");
        Assert.notNull(roundId, "roundId不能为空");
    }


    @Override
    public void setBody(Map<String, String> body) {
        Assert.notEmpty(body,"请您传入参数");
        body.put("roundId", "nutrientsType");
        setBody(body.get("roundId"));
    }

    public void setBody(@NonNull String roundId) {
        param.set("body", JsonFormatUtil.formatKeyValueToStr(
                Arrays.asList("roundId","monitor_refer"),
                Arrays.asList(roundId,"plant_receiveNutrients")
        ));
    }


}
