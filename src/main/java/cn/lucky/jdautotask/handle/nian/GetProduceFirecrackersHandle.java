package cn.lucky.jdautotask.handle.nian;

import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.nian.impl.NianCollectProduceScoreRequest;
import cn.lucky.jdautotask.handle.nian.impl.NianGetHomeDataRequest;
import cn.lucky.jdautotask.handle.nian.impl.NianRaiseRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.AssertUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
 * 每个小时自动领取产生的爆竹
 */
@Log4j2
public class GetProduceFirecrackersHandle extends AbstractJdAutoTaskHandle {


    private String cookie;

    private JsonNode homeMainInfo;

    private String secretp;

    //可收取的爆竹
    private Long produceScore;

    private Long remainScore;

    private Long curLevelStartScore;

    private Long nextLevelScore;


    @Override
    public void doExecute(@NonNull JdAutoTaskRequest jdAutoTaskRequest) {

        AssertUtil.strNotNull(jdAutoTaskRequest.getCookie(), "cookie不能为空");
        cookie = jdAutoTaskRequest.getCookie();
        /**
         * 获取首页json
         */
        NianGetHomeDataRequest nianGetHomeDataRequest = new NianGetHomeDataRequest();
        nianGetHomeDataRequest.setCookie(cookie);

        try {
            String execute = nianGetHomeDataRequest.execute(restTemplate);
            JsonNode jsonNode = objectMapper.readTree(execute);
            if (jsonNode.get("code")==null || !"0".equals(jsonNode.get("code").asText())
                || jsonNode.get("data")==null || !"0".equals(jsonNode.get("data").get("bizCode").asText())
            ) {
                log.warn("炸年兽请求首页数据失败");
                return;
            }
            homeMainInfo = jsonNode.get("data").get("result").get("homeMainInfo");
            secretp = homeMainInfo.get("secretp").asText();
            AssertUtil.strNotNull(secretp, "账号被风控");
            produceScore = homeMainInfo.get("raiseInfo").get("produceScore").asLong();
            remainScore = homeMainInfo.get("raiseInfo").get("remainScore").asLong();
            curLevelStartScore = homeMainInfo.get("raiseInfo").get("curLevelStartScore").asLong();
            nextLevelScore = homeMainInfo.get("raiseInfo").get("nextLevelScore").asLong();


            //领取爆竹
            if (produceScore > 0) {
                collectProduceScore();
            }
            //判断是否可以升级
            if (remainScore >= nextLevelScore - curLevelStartScore) {
                raise();
            } else {
                log.info("当前爆竹不够炸年兽，还差：{}",nextLevelScore - curLevelStartScore-remainScore);
            }


        } catch (Exception e) {
            log.warn("领取每个小时产生的爆竹异常");
            e.printStackTrace();
        }
    }

    private void raise() throws JsonProcessingException, InterruptedException {
        log.info("开始炸年兽");
        NianRaiseRequest nianRaiseRequest = new NianRaiseRequest();
        nianRaiseRequest.setCookie(cookie);
        nianRaiseRequest.setBody(secretp);
        String execute = nianRaiseRequest.execute(restTemplate);
        log.info("炸年兽成功，返回json：{}", execute);
    }

    //收取产生的爆竹
    private void collectProduceScore() throws JsonProcessingException, InterruptedException {
        log.info("开始领取爆竹");
        NianCollectProduceScoreRequest nianCollectProduceScoreRequest = new NianCollectProduceScoreRequest();
        nianCollectProduceScoreRequest.setCookie(cookie);
        nianCollectProduceScoreRequest.setBody(secretp);
        String execute = nianCollectProduceScoreRequest.execute(restTemplate);
        log.info("爆竹领取成功，返回json：{}", execute);
    }
}
