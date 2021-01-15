package cn.lucky.jdautotask.handle.plantBeanIndex;

import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.plantBeanIndex.impl.PlantBeanIndexRequest;
import cn.lucky.jdautotask.pojo.plantBeanIndex.Round;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

/*
 * @Author zyl
 * @Description 种豆得豆
 * @Date 2021/1/15 16:05
 **/
@Slf4j
public class PlantBeanIndexHandle extends AbstractJdAutoTaskHandle {

    //cookie必填
    private String cookie;

    private ObjectMapper objectMapper;

    //当前roundId
    private String currentRoundId;

    //上期roundId
    private String lastRoundId;


    public PlantBeanIndexHandle() {
        super(Collections.singletonMap("restTemplate", RestTemplateConfig.getRestTemplateStringIsUtf8()));
        objectMapper = JsonFormatUtil.getObjectMapper();
    }

    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) {
        AssertUtil.strNotNull(jdAutoTaskRequest.getCookie(), "cookie不能为空");
        cookie = jdAutoTaskRequest.getCookie();
        /**
         * 这里获取种豆得豆详情
         */
        PlantBeanIndexRequest plantBeanIndexRequest = new PlantBeanIndexRequest();
        plantBeanIndexRequest.setCookie(cookie);
        String resultStr = plantBeanIndexRequest.execute(restTemplate);
        /**
         * 使用JsonNode解析
         */
        try {
            JsonNode jsonNode = objectMapper.readTree(resultStr);
            //判断状态码
            String code = jsonNode.get("code").asText();
            if (!"0".equals(code)) {
                log.warn("key:{}，请求失败", cookie);
            }
            //获取date数据
            JsonNode data = jsonNode.get("data");
            //解析round
            JsonNode roundList = data.get("roundList");

            currentRoundId = roundList.get(1).get("roundId").asText();

            lastRoundId = roundList.get(0).get("roundId").asText();


            receiveNutrients();




        } catch (JsonProcessingException e) {
            log.error("解析json失败，失败原因：{}",e);
        }
    }

    /*
     * @Author zyl
     * @Description 定时领取营养液
     * @Date 2021/1/15 17:17
     * @Param []
     * @return void
     **/
    private void receiveNutrients() {


    }


}
