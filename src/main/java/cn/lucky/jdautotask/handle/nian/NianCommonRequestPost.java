package cn.lucky.jdautotask.handle.nian;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

import java.util.Map;

/**
 * 年兽活动通用的请求
 */
public class NianCommonRequestPost extends AbstractNianShouRequestPost {


    private NianCommonRequestPost() {
    }

    public NianCommonRequestPost(@NonNull String functionId) {
        setFunctionId(functionId);
    }

    public NianCommonRequestPost(@NonNull String functionId, @NonNull String body) {
        setFunctionId(functionId);
        setBodyByStr(body);
    }

    public NianCommonRequestPost(@NonNull String functionId, @NonNull Map<String,Object> body) {
        setFunctionId(functionId);
        setBodyByValueObject(body);
    }

    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
