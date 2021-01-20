package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.AssertUtil;
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
 * 领取产生的营养液
 */
@Log4j2
public class CultureBeanPBIRequest extends AbstractRequestPlantBeanIndex {

    public CultureBeanPBIRequest(){
        param.set("functionId", "cultureBean");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
    }

    public CultureBeanPBIRequest(String cookie){
        param.set("functionId", "cultureBean");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
        AssertUtil.strNotNull(cookie,"cookie不能为空");
        setCookie(cookie);
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode body = objectMapper.readTree(this.getParam().get("body").get(0));
        Assert.notNull(body.get("roundId"),"roundId不能为空");
        Assert.notNull(body.get("nutrientsType"),"nutrientsType不能为空");
    }

    public void setBody(@NonNull String roundId, @NonNull String nutrientsType) {
        Map<String, String> map = new HashMap<>();
        map.put("roundId", roundId);
        map.put("nutrientsType", nutrientsType);
        setBody(map);
    }
}
