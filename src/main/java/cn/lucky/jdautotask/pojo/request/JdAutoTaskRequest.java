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

    //用户设备标识
    private String userAgent;

    //cookie是否失效  0-失效，1-未失效
    private Boolean cookieFailure;

    //用户名称
    private String nickname;
}
