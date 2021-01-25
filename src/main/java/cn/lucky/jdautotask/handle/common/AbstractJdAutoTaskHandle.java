package cn.lucky.jdautotask.handle.common;

import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.pojo.superMarket.BizResule;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/*
 * @Author zyl
 * @Description jd自动任务处理逻辑实体类
 * @Date 2021/1/15 15:37
 **/
public abstract class AbstractJdAutoTaskHandle implements AutoTaskLogic<JdAutoTaskRequest> {


    protected RestTemplate restTemplate;

    protected ObjectMapper objectMapper;

    public AbstractJdAutoTaskHandle(){
        restTemplate=RestTemplateConfig.getRestTemplateStringIsUtf8();
        objectMapper = JsonFormatUtil.getObjectMapper();
    }

    /*
     * @Author zyl
     * @Description restTemplate必须设置
     * @Date 2021/1/15 15:49
     * @Param [beanMap]
     * @return
     **/
    public AbstractJdAutoTaskHandle(Map<String, Object> beanMap){
        setBeans(beanMap);
    }

    @Override
    public void setBeans(Map<String, Object> beanMap) {
        Assert.notNull(beanMap.get("restTemplate"), "请求实体不能为空");
        restTemplate = (RestTemplate) beanMap.get("restTemplate");
    }


    /**
     * 校验东东超市请求返回是否成功业务调用
     * @param jsonNode
     * @return
     */
    public Boolean checkResponseOnlyCode(JsonNode jsonNode) {
        if (jsonNode == null) {
            return false;
        }
        if (jsonNode.get("code") == null ||
                !"0".equals(jsonNode.get("code").asText())
        ) {
            return false;
        }

        return true;
    }



    /**
     * 校验东东超市请求返回是否成功业务调用
     * @param jsonNode
     * @return
     */
    public Boolean checkResponseToBiz(JsonNode jsonNode) {
        if (jsonNode.get("code") == null ||
                !"0".equals(jsonNode.get("code").asText()) ||
                jsonNode.get("data") == null ||
                jsonNode.get("data").get("bizCode") == null ||
                !"0".equals(jsonNode.get("data").get("bizCode").asText())
        ) {
            return false;
        }

        return true;
    }

    /**
     * 校验东东超市请求返回是否成功业务调用
     * @param jsonNode
     * @return
     */
    public BizResule checkResponseToBizReBiz(JsonNode jsonNode) {

        BizResule bizResule = new BizResule();
        if (jsonNode.get("code") == null ||
                !"0".equals(jsonNode.get("code").asText()) ||
                jsonNode.get("data") == null ||
                jsonNode.get("data").get("bizCode") == null ||
                !"0".equals(jsonNode.get("data").get("bizCode").asText())
        ) {
            if (jsonNode.get("data").get("bizCode") != null) {
                bizResule.setBizCode(jsonNode.get("data").get("bizCode").asInt());
                bizResule.setBizMsg(jsonNode.get("data").get("bizMsg").asText());
                return bizResule;
            } else {
                bizResule.setBizCode(-1);
                bizResule.setBizMsg("fail");
                return bizResule;
            }
        }

        bizResule.setResult(true);
        bizResule.setBizCode(jsonNode.get("data").get("bizCode").asInt());
        bizResule.setBizMsg(jsonNode.get("data").get("bizMsg").asText());
        return bizResule;
    }
}
