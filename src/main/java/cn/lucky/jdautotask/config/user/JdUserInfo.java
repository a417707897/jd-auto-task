package cn.lucky.jdautotask.config.user;

import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.handle.user.QueryJDUserInfoRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    public JdUserInfo() {
        restTemplate = RestTemplateConfig.getRestTemplateStringIsUtf8();
        objectMapper = JsonFormatUtil.getObjectMapper();
        jdAutoTaskRequestMap = new ConcurrentHashMap<>();
        jdAutoTaskRequestMap.put("18337656372_p", JdAutoTaskRequest.builder()
                .cookieFailure(false)
                .cookie("pt_key=AAJgDsdeADDg5uvoQcTdzYDZc0e33YQahEttMPRA3Bf6POdTNT4NrWeX_03Y3Lib-hTORP2M5VI;pt_pin=18337656372_p").build());

        jdAutoTaskRequestMap.put("jd_SBznbkgNHMvQ", JdAutoTaskRequest.builder()
                .cookieFailure(false)
                .cookie("pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ").build());
    }

    /**
     * 校验用户信息，并初始化一些信息
     */
    @PostConstruct
    private void init() {
        jdAutoTaskRequestMap.forEach((key,value)->{
            QueryJDUserInfoRequest queryJDUserInfoRequest =
                    new QueryJDUserInfoRequest();
            queryJDUserInfoRequest.setCookie(value.getCookie());
            String execute = queryJDUserInfoRequest.execute(restTemplate);
            try {
                JsonNode node = objectMapper.readTree(execute);
                if (node.get("retcode") != null && 0==node.get("retcode").asInt()) {
                    //设置cookie未失效，并设置用户名
                    value.setCookieFailure(true);
                    value.setNickname(node.get("base").get("nickname").asText());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        System.out.println(1);
    }

    public Map<String, JdAutoTaskRequest> getJdAutoTaskRequestMap() {
        return jdAutoTaskRequestMap;
    }
}
