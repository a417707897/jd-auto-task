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
 * 领取产生的营养液
 */
@Slf4j
public class CultureBeanPBIRequest extends AbstractRequestPlantBeanIndex {

    public CultureBeanPBIRequest(){
        param.set("functionId", "cultureBean");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        requestWayType = RequestWayType.POST;
    }


    @Override
    public void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode body = objectMapper.readTree(this.getParam().get("body").get(0));
        Assert.notNull(body.get("roundId"),"roundId不能为空");
        Assert.notNull(body.get("nutrientsType"),"nutrientsType不能为空");
    }

    @Override
    public void setBody(Map<String, String> body) {
        Assert.notEmpty(body,"请您传入参数");
        body.put("roundId", "nutrientsType");
        setBody("nutrientsType", body.get("nutrientsType"));
    }

    public void setBody(@NonNull String roundId, @NonNull String nutrientsType) {
        param.set("body", JsonFormatUtil.formatKeyValueToStr(
                Arrays.asList("roundId","nutrientsType"),
                Arrays.asList(roundId,nutrientsType)
        ));
    }
}