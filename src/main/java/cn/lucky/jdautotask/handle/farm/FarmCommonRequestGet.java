package cn.lucky.jdautotask.handle.farm;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.Map;

/**
 * 东东农场通用的请求实体
 */
public class FarmCommonRequestGet extends AbstractRequestFarmGet{

    private FarmCommonRequestGet() {
    }

    public FarmCommonRequestGet(@NonNull String functionId) {
        setFunctionId(functionId);
    }

    public FarmCommonRequestGet(@NonNull String functionId, @NonNull String body) {
        setFunctionId(functionId);
        setBodyByStr(body);
    }

    public FarmCommonRequestGet(@NonNull String functionId, @NonNull Map<String,Object> body) {
        setFunctionId(functionId);
        setBodyByValueObject(body);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
