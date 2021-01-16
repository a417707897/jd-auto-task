package cn.lucky.jdautotask.handle.plantBeanIndex.impl;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取好友列表
 */
@Log4j2
public class PlantFriendListPBIRequest extends AbstractRequestPlantBeanIndex {


    public PlantFriendListPBIRequest(){
        param.set("functionId", "plantFriendList");
        //设置请求url
        url = "https://api.m.jd.com/client.action";
        //设置请求方式
        httpMethod = HttpMethod.POST;
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonFormatUtil.getObjectMapper();
        JsonNode body = objectMapper.readTree(param.get("body").get(0));
        AssertUtil.strNotNull(body.get("pageNum").asText(), "pageNum参数为空");
    }

    public void setBody(@NonNull String pageNum){
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        setBody(map);
    }
}
