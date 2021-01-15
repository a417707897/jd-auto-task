package cn.lucky.jdautotask.pojo.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * jd自动任务请求实体
 */
@Data
@Builder
public class JdAutoTaskRequest {

    private String cookie;

    private String userAgent;

    private Map<String, String> body;
}
