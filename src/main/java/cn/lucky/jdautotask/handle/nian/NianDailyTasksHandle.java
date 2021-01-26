package cn.lucky.jdautotask.handle.nian;

import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.nian.dailyTasks.NianCollectScoreRequest;
import cn.lucky.jdautotask.handle.nian.dailyTasks.NianGetTaskDetailRequest;
import cn.lucky.jdautotask.handle.nian.impl.NianGetHomeDataRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.AssertUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * 年兽日常活动处理逻辑
 */
@Log4j2
public class NianDailyTasksHandle extends AbstractJdAutoTaskHandle {

    private String secretp;

    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) throws InterruptedException {
        AssertUtil.strNotNull(jdAutoTaskRequest.getCookie(), "cookie不能为空");
        cookie = jdAutoTaskRequest.getCookie();
        log.info("年兽活动，日常活动开始执行");
        //获取任务列表
        try {
            //获取年兽首页数据
            /**
             * 获取首页json
             */
            NianGetHomeDataRequest nianGetHomeDataRequest = new NianGetHomeDataRequest();
            nianGetHomeDataRequest.setCookie(cookie);
            String execute1 = nianGetHomeDataRequest.execute(restTemplate);
            JsonNode nianGetHomeData = objectMapper.readTree(execute1);
            if (!checkResponseToBiz(nianGetHomeData)) {
                log.warn("炸年兽请求首页数据失败,返回json:{}", execute1);
                return;
            }
            secretp = nianGetHomeData.get("data").get("result").get("homeMainInfo").get("secretp").asText();


            NianGetTaskDetailRequest nianGetTaskDetailRequest = new NianGetTaskDetailRequest();
            nianGetTaskDetailRequest.setCookie(cookie);
            String execute = nianGetTaskDetailRequest.execute(restTemplate);
            JsonNode nianGetTaskDetail = objectMapper.readTree(execute);
            if (!checkResponseToBiz(nianGetTaskDetail)) {
                log.warn("任务列表获取失败，返回json：{}", execute);
                return;
            }
            //获取任务详情
            JsonNode taskVos = nianGetTaskDetail.get("data").get("result").get("taskVos");
            if (taskVos == null || taskVos.size() == 0) {
                log.warn("任务列表为空");
                return;
            }
            Integer taskType = -1;
            String taskId = null;
            Integer status = -1;
            for (JsonNode taskVo : taskVos) {
                taskType = taskVo.get("taskType").asInt();
                taskId = taskVo.get("taskId").asText();
                status = taskVo.get("status").asInt();
                if (status == 2) {
                    log.info("任务：{},已经做完了呦！", taskVo.get("taskName").asText());
                    continue;
                }

                log.info("开始做：【{}】任务", taskVo.get("taskName").asText());
                if (taskType == 2) {
                    taskTypeTow(taskId, taskVo);
                } else if (taskType == 3 || taskType == 26) {
//                    taskTypeThreeOrTwentySix()
                }

            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    /*
     * @Author zyl
     * @Description 优选佳品任务
     * @Date 2021/1/26 14:54
     * @Param [taskId, taskVo]
     * @return void
     **/
    private void taskTypeTow(@NonNull String taskId, @NonNull JsonNode taskVo) {
        //获取可加购的商品列表
        NianCommonRequestPost nianCommonRequestPost = null;
        //任务请求实体
        NianCollectScoreRequest nianCollectScoreRequest = null;

        try {
            nianCommonRequestPost =
                    new NianCommonRequestPost("nian_getFeedDetail","{\"taskId\": "+taskId+"}");
            nianCommonRequestPost.setCookie(cookie);
            String execute = nianCommonRequestPost.execute(restTemplate);
            JsonNode nianGetFeedDetail = objectMapper.readTree(execute);
            if (!checkResponseToBiz(nianGetFeedDetail)) {
                log.warn("优选佳品任务列表请求失败，返回json：{}", execute);
                return;
            }
            //获取可添加产品的列表
            JsonNode addProductVos = nianGetFeedDetail.get("data").get("result").get("addProductVos");
            if (addProductVos==null || addProductVos.size()==0) {
                log.warn("优选佳品任务列表获取失败");
                return;
            }

            Integer timesVo = -1;
            Integer maxTimesVo = -1;
            String taskIdVo = null;
            JsonNode productInfoVos = null;
            for (JsonNode addProductVo : addProductVos) {
                if (1!=addProductVo.get("status").asInt()) {
                    log.info("任务：{}，可能已经完成", addProductVo.get("subTitleName").asText());
                    continue;
                }
                timesVo = addProductVo.get("times").asInt();
                maxTimesVo = addProductVo.get("maxTimes").asInt();
                taskIdVo = addProductVo.get("taskId").asText();
                productInfoVos = addProductVo.get("productInfoVos");

                for (int i = 0; i < productInfoVos.size() && maxTimesVo>timesVo+i; i++) {
                    JsonNode productInfoVo = productInfoVos.get(i);
                    //说明是已经做过的商品
                    if (1 != productInfoVo.get("status").asInt()) {
                        continue;
                    }
                    nianCollectScoreRequest = new NianCollectScoreRequest();
                    nianCollectScoreRequest.setCookie(cookie);
                    nianCollectScoreRequest.setBody(secretp,taskIdVo,productInfoVo.get("itemId").asText(),
                            null,null,null);
                    String execute1 = nianCollectScoreRequest.execute(restTemplate);

                    JsonNode nianCollectScore = objectMapper.readTree(execute1);
                    if (checkResponseToBiz(nianCollectScore)) {
                        log.info("任务已完成，获取{}个爆竹", nianCollectScore.get("data").get("result").get("score").asText());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
