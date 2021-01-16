package cn.lucky.jdautotask.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json格式化工具
 */
@Slf4j
public class JsonFormatUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 格式化Map格式的数据，key-value格式
     * @param jsonName
     * @param jsonValue
     */
    public static synchronized String formatKeyValueToStr(@NonNull List<String> jsonName, @NonNull List<String> jsonValue) {
        try {
            if (jsonName.size() != jsonValue.size()) {
                throw new IllegalArgumentException("jsonName和jsonValue数量不一致");
            }
            Map<String, String> formatMap = new HashMap<>();
            for (int i = 0; i < jsonName.size(); i++) {
                formatMap.put(jsonName.get(i), jsonValue.get(i));
            }
            return objectMapper.writeValueAsString(formatMap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized String jsonFormatObjectToStr(Object obi) {

        try {
            return objectMapper.writeValueAsString(obi);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized String formatJsonNodeToStr(JsonNode jsonNode){
        Assert.notNull(jsonNode, "json不能为空");
        String jsonStr = jsonNode.toString();
        jsonStr.substring(1, jsonStr.length() - 1);
        return jsonStr;
    }

    public static synchronized ObjectMapper getObjectMapper(){
        return objectMapper;
    }

}
