package cn.lucky.jdautotask.handle.superMarket;

import cn.hutool.core.util.StrUtil;
import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.superMarket.dailyTasks.SuperMarketSmtgDoShopTaskRequest;
import cn.lucky.jdautotask.handle.superMarket.dailyTasks.SuperMarketSmtgObtainShopTaskPrizeRequest;
import cn.lucky.jdautotask.handle.superMarket.dailyTasks.SuperMarketSmtgQueryShopTaskRequest;
import cn.lucky.jdautotask.handle.superMarket.other.SuperMarketBeanSignRequest;
import cn.lucky.jdautotask.handle.superMarket.other.SuperMarketReceiveBlueCoinRequest;
import cn.lucky.jdautotask.handle.superMarket.other.SuperMarketSmtgSignRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.pojo.superMarket.BizResule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.omg.PortableInterceptor.INACTIVE;

import javax.activation.URLDataSource;

/**
 * 东东超市日常任务处理
 */
@Log4j2
public class SuperMarketDailyTasksHandle extends AbstractJdAutoTaskHandle {


    private String cookie;

    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) throws InterruptedException {
        cookie = jdAutoTaskRequest.getCookie();
        log.info("开始做京东日常任务");

        try {
            //每日签到
            SuperMarketSmtgSignRequest superMarketSmtgSignRequest = new SuperMarketSmtgSignRequest();
            superMarketSmtgSignRequest.setCookie(cookie);
            String execute = superMarketSmtgSignRequest.execute(restTemplate);
            JsonNode jsonNode = objectMapper.readTree(execute);
            BizResule bizResule = checkResponseToBizReBiz(jsonNode);
            if (bizResule.getResult()) {
                log.warn("东东超市每日签到成功");
            } else {
                log.warn("东东超市每日签到是失败，原因：{}", bizResule.getBizMsg());
            }

            //每天从指定入口进入游戏,可获得额外奖励
            SuperMarketBeanSignRequest superMarketBeanSignRequest = new SuperMarketBeanSignRequest();
            superMarketBeanSignRequest.setCookie(cookie);
            String execute1 = superMarketBeanSignRequest.execute(restTemplate);
            jsonNode = objectMapper.readTree(execute1);
            bizResule = checkResponseToBizReBiz(jsonNode);
            if (bizResule.getResult()) {
                log.warn("每天从指定入口进入游戏,可获得额外奖励，请求成功");
            } else {
                log.warn("每天从指定入口进入游戏,可获得额外奖励，请求失败，原因：{}", bizResule.getBizMsg());
            }

            //收取小费
            log.info("开始收取小费");
            SuperMarketReceiveBlueCoinRequest superMarketReceiveBlueCoinRequest
                    = new SuperMarketReceiveBlueCoinRequest();
            //设置访问频率为5秒一次
            superMarketReceiveBlueCoinRequest.setRequestInterval(5000L);

            while (true) {
                superMarketReceiveBlueCoinRequest.setCookie(cookie);
                String execute2 = superMarketReceiveBlueCoinRequest.execute(restTemplate);
                jsonNode = objectMapper.readTree(execute2);
                bizResule = checkResponseToBizReBiz(objectMapper.readTree(execute2));
                if (bizResule.getResult()) {
                    log.info("小费蓝币领取成功，领取：{}个，当前蓝币：{}", jsonNode.get("data").get("result").get("receivedBlue").asLong(),
                            jsonNode.get("data").get("result").get("totalBlue").asLong());
                } else {
                    log.warn("小费领取失败，原因：{}", bizResule.getBizMsg());
                    break;
                }
            }


            doTask();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    private void doTask() {

        SuperMarketSmtgQueryShopTaskRequest superMarketSmtgQueryShopTaskRequest = new SuperMarketSmtgQueryShopTaskRequest();
        superMarketSmtgQueryShopTaskRequest.setCookie(cookie);

        try {

            //获取京东日常任务列表
            String smtgQueryShopTask = superMarketSmtgQueryShopTaskRequest.execute(restTemplate);

            JsonNode smtgQueryShopTaskJson = objectMapper.readTree(smtgQueryShopTask);

            if (!checkResponseToBiz(smtgQueryShopTaskJson)) {
                log.warn("获取日常任务列表失败，返回json：{}", smtgQueryShopTask);
            }
            //任务类型
            Integer type = -1;
            //任务状态
            Integer taskStatus = -1;
            //奖励领取状态
            Integer prizeStatus = -1;
            //任务名称
            String title = "";
            //任务id
            String taskId = "";
            //完成任务的次数
            Integer finishNum = 0;
            //需要完成任务的次数
            Integer targetNum = 0;

            //需要做任务的次数
            Integer needTaskNum = 6;

            //循环做任务
            for (JsonNode taskJsonNode : smtgQueryShopTaskJson.get("data").get("result").get("taskList")) {
                type = taskJsonNode.get("type").asInt();
                taskStatus = taskJsonNode.get("taskStatus").asInt();
                prizeStatus = taskJsonNode.get("prizeStatus").asInt();
                finishNum = taskJsonNode.get("finishNum").asInt();
                targetNum = taskJsonNode.get("targetNum").asInt();
                title = taskJsonNode.get("title").asText();
                taskId = taskJsonNode.get("taskId").asText();

                if (taskStatus == 1 && targetNum - finishNum == 0) {
                    if (prizeStatus == 0) {
                        log.info("日常任务:【{}】，已完成，奖励已领取", title);
                        needTaskNum--;
                    } else {
                        log.info("日常任务:【{}】，已完成，奖励未领取", title);
                        //领取奖励
                        SuperMarketSmtgObtainShopTaskPrizeRequest superMarketSmtgObtainShopTaskPrizeRequest
                                = new SuperMarketSmtgObtainShopTaskPrizeRequest();
                        superMarketSmtgObtainShopTaskPrizeRequest.setCookie(cookie);
                        superMarketSmtgObtainShopTaskPrizeRequest.setBody(taskId);
                        String execute = superMarketSmtgObtainShopTaskPrizeRequest.execute(restTemplate);
                        JsonNode jsonNode = objectMapper.readTree(execute);
                        BizResule bizResule = checkResponseToBizReBiz(jsonNode);
                        if (bizResule.getResult()) {
                            log.info("日常任务:【{}】，奖励领取成功", title);
                        } else {
                            log.info("日常任务:【{}】，奖励领取失败，原因：{},", title, bizResule.getBizMsg());
                            throw new RuntimeException("日常任务奖励领取失败,原因：" + bizResule.getBizMsg());
                        }
                    }
                    continue;
                }
                //开始做任务
                log.info("开始做日常任务：【{}】，需要做次数：{}", title,targetNum-finishNum);
                SuperMarketSmtgDoShopTaskRequest superMarketSmtgDoShopTaskRequest = null;

                for (Integer i = 0; i < targetNum-finishNum; i++) {
                    superMarketSmtgDoShopTaskRequest = new SuperMarketSmtgDoShopTaskRequest();
                    if (1 == type || 11 == type) {
                        superMarketSmtgDoShopTaskRequest.setCookie(cookie);
                        superMarketSmtgDoShopTaskRequest.setBody(taskId);
                        String execute = superMarketSmtgDoShopTaskRequest.execute(restTemplate);
                        JsonNode jsonNode = objectMapper.readTree(execute);

                        BizResule bizResule = checkResponseToBizReBiz(jsonNode);
                        if (!bizResule.getResult()) {
                            log.warn("日常任务：【{}】，请求异常，json：{}", title, execute);
                        } else {
                            log.warn("日常任务：【{}】，请求完成，结果：{}", title, bizResule.getBizMsg());
                        }
                    } else if (2 == type || 8 == type || 10 == type) {
                        //获取itemId
                        JsonNode content = taskJsonNode.get("content");
                        if (content == null) {
                            log.warn("日常任务:【{}】，无content信息", title);
                            continue;
                        }

                        JsonNode itemIdJsonNode = content.get(type + "");
                        if (itemIdJsonNode == null) {
                            log.warn("日常任务:【{}】，无itemId信息", title);
                            continue;
                        }

                        Long itemId = itemIdJsonNode.get("itemId").asLong();
                        if (itemId == null) {
                            log.warn("日常任务:【{}】，itemId为空", title);
                            continue;
                        }

                        superMarketSmtgDoShopTaskRequest.setCookie(cookie);
                        superMarketSmtgDoShopTaskRequest.setBody(taskId, itemId);
                        String execute = superMarketSmtgDoShopTaskRequest.execute(restTemplate);
                        JsonNode jsonNode = objectMapper.readTree(execute);

                        BizResule bizResule = checkResponseToBizReBiz(jsonNode);
                        if (!bizResule.getResult()) {
                            log.warn("日常任务：【{}】，请求异常，json：{}", title, execute);
                        } else {
                            log.warn("日常任务：【{}】，请求完成，结果：{}", title, bizResule.getBizMsg());
                        }
                    } else {
                        log.warn("日常任务：【{}】，无处理模块", title);
                    }
                }
            }

            if (needTaskNum > 0) {
                doTask();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
