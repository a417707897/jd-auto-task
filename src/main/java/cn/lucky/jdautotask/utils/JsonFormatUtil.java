package cn.lucky.jdautotask.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
    public static String formatKeyValueToStr(@NonNull List<String> jsonName, @NonNull List<String> jsonValue) throws JsonProcessingException {
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

}
