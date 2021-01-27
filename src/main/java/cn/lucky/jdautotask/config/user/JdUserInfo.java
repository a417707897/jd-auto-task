package cn.lucky.jdautotask.config.user;

import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 京东用户信息配置
 */
@Component
public class JdUserInfo {

    private Map<String, JdAutoTaskRequest> jdAutoTaskRequestMap = null;

    public JdUserInfo() {
        jdAutoTaskRequestMap = new ConcurrentHashMap<>();
        jdAutoTaskRequestMap.put("18337656372_p", JdAutoTaskRequest.builder()
                .cookieFailure(0)
                .cookie("pt_key=AAJgDsdeADDg5uvoQcTdzYDZc0e33YQahEttMPRA3Bf6POdTNT4NrWeX_03Y3Lib-hTORP2M5VI;pt_pin=18337656372_p").build());

        jdAutoTaskRequestMap.put("jd_SBznbkgNHMvQ", JdAutoTaskRequest.builder()
                .cookieFailure(0)
                .cookie("pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ").build());
    }

    /**
     * 校验用户信息，并初始化一些信息
     */
    @PostConstruct
    private void init() {



    }
}
