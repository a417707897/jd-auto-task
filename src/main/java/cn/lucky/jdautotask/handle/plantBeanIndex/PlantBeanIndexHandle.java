package cn.lucky.jdautotask.handle.plantBeanIndex;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.dialect.log4j2.Log4j2Log;
import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.plantBeanIndex.impl.*;
import cn.lucky.jdautotask.pojo.plantBeanIndex.BubbleInfos;
import cn.lucky.jdautotask.pojo.plantBeanIndex.DailyTasks;
import cn.lucky.jdautotask.pojo.plantBeanIndex.FriendInfo;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @Author zyl
 * @Description 种豆得豆
 * @Date 2021/1/15 16:05
 **/
@Log4j2
public class PlantBeanIndexHandle extends AbstractJdAutoTaskHandle {

    //cookie必填
    private String cookie;

    //当前roundId
    private String currentRoundId;

    //上期roundId
    private String lastRoundId;

    private String userName;

    List<DailyTasks> dailyTasksList;

    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) {
        log.debug("deeeeee");
        AssertUtil.strNotNull(jdAutoTaskRequest.getCookie(), "cookie不能为空");
        cookie = jdAutoTaskRequest.getCookie();
        /**
         * 这里获取种豆得豆详情
         */
        PlantBeanIndexPBIRequest plantBeanIndexRequest = new PlantBeanIndexPBIRequest();
        plantBeanIndexRequest.setCookie(cookie);
        /**
         * 使用JsonNode解析
         */
        try {
            String resultStr = plantBeanIndexRequest.execute(restTemplate);
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

            userName = data.get("plantUserInfo").get("plantNickName").asText();

            dailyTasksList = objectMapper.readValue(JsonFormatUtil.formatJsonNodeToStr(data.get("taskList")), new TypeReference<List<DailyTasks>>() {
            });

            //领取自己的营养液
            receiveNutrients();
            //”帮助“好友收取营养液
            helpFriendsCollect();
            //做日常任务
            dailyTasks();




            //领取活动或偷取产生的营养液
            cultureBean(roundList.get(1).get("bubbleInfos"));


        } catch (JsonProcessingException e) {
            log.error("解析json失败，失败原因：{}",e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 做日常任务
     */
    private void dailyTasks() {
        log.info("{},开始做日常任务",userName);
        if (dailyTasksList == null || dailyTasksList.size() == 0) {
            log.warn("{},日常任务列表获取失败",userName);
            return;
        }

        ReceiveNutrientsTaskPBIRequest receiveNutrientsTaskPBIRequest = null;
        for (DailyTasks dailyTasks : dailyTasksList) {
            if (1==dailyTasks.getIsFinished()) {
                log.info("{},任务名称：{},已经完成", userName, dailyTasks.getTaskName());
                continue;
            }

            if (8==dailyTasks.getTaskType()) {
                log.info("{},任务未完成,{}需自行手动去京东APP完成,{}营养液",userName,dailyTasks.getTaskName(),dailyTasks.getDesc());
                continue;
            }

            if (1==dailyTasks.getDailyTimes()) {
                log.info("{},领取营养液任务,任务名称：{}",userName,dailyTasks.getTaskName());
                receiveNutrientsTaskPBIRequest = new ReceiveNutrientsTaskPBIRequest();
                receiveNutrientsTaskPBIRequest.setCookie(cookie);
                receiveNutrientsTaskPBIRequest.setBody(dailyTasks.getAwardType().toString());
                String execute = receiveNutrientsTaskPBIRequest.execute(restTemplate);
                /**
                 * {"code":"0","data":{"nutrState":"1","nutrNum":1,"nutrToast":"恭喜你获得营养液，快去培养小小豆吧"}}
                 * {"code":"0","data":{"nutrState":"3","nutrNum":0,"nutrToast":""}}
                 */




            }




        }



    }

    /**
     * ”帮助“好友收取营养液
     */
    private void helpFriendsCollect() throws JsonProcessingException {

        log.info("{},开始收取好友的营养液",userName);

        int i = 1;
        List<FriendInfo> friendInfosList = new ArrayList<>();
        //循环获取偷取的好友列表
        while (true) {
            List<FriendInfo> friendInfos = plantFriendList(i++);
            if (friendInfos == null) {
                break;
            }
            if (friendInfos.size()>0) {
                friendInfosList.addAll(friendInfos);
            }
        }

        if (friendInfosList.size() > 0) {
            //偷取好友
            CollectUserNutrPBIRequest collectUserNutrPBIRequest = new CollectUserNutrPBIRequest();
            collectUserNutrPBIRequest.setCookie(cookie);
            for (FriendInfo friendInfo : friendInfosList) {
                log.info("{},开始偷取好友：{}的营养液",userName,friendInfo.getPlantNickName());
                collectUserNutrPBIRequest.setBody(friendInfo.getParadiseUuid(), currentRoundId);
                String execute = collectUserNutrPBIRequest.execute(restTemplate);
                JsonNode jsonNode = objectMapper.readTree(execute);
                String code = jsonNode.get("code").asText();

                if (!"0".equals(code)) {
                    log.warn("{},偷取好友营养液失败",userName);
                    return;
                }
                JsonNode data = jsonNode.get("data");
                if (data == null) {
                    return;
                }
                if ("1".equals(data.get("collectResult").asText())) {
                    log.info("{},偷取成功，返回结果:{}",userName, execute);
                } else if ("3".equals(data.get("collectResult").asText())) {
                    log.info("{},偷取成功，返回结果:{}", userName,data.get("collectMsg").asText());
                    return;
                } else {
                    log.info("{}，失败的请求",userName);
                }
                /**
                 * {"code":"0","data":{"collectResult":"1","friendNutrRewards":"1","collectNutrRewards":"1",
                 * "collectMsg":"成功帮*plantNickName*收取*friendNutrRewards*瓶营养液，恭喜获得*collectNutrRewards*瓶奖励",
                 * "timeNutrientsRes":{"state":"3","countDown":"1511","bottleState":"2","nextReceiveTime":"1610790398000",
                 * "nutrCount":"0","nutrCountLimit":"3","endTimeNutrCount":"1"}}}
                 */

            }


        }


    }

    public List<FriendInfo> plantFriendList(Integer pageNum) throws JsonProcessingException {
        PlantFriendListPBIRequest plantFriendListPBIRequest = new PlantFriendListPBIRequest();
        plantFriendListPBIRequest.setCookie(cookie);
        plantFriendListPBIRequest.setBody(pageNum.toString());
        String execute = plantFriendListPBIRequest.execute(restTemplate);
        //解析
        JsonNode plantFriendList = objectMapper.readTree(execute);
        //判断code
        String code = plantFriendList.get("code").asText();
        if (!"0".equals(code)) {
            log.info("{},种豆得豆，朋友列表请求失败",userName);
            return null;
        }

        JsonNode data = plantFriendList.get("data");
        if (data == null) {
            return null;
        }

        JsonNode friendInfoList = data.get("friendInfoList");
        if (friendInfoList == null || friendInfoList.size()==0) {
            log.info("{},种豆得豆，宁没有朋友哦！！！",userName);
            return null;
        }
        //解析json
        List<FriendInfo> friendInfosList = new ArrayList<>();
        List<FriendInfo> friendInfos = objectMapper.readValue(JsonFormatUtil.formatJsonNodeToStr(friendInfoList), new TypeReference<List<FriendInfo>>() {
        });
        //判断可偷取的好友，条件是营养液大于等2
        for (FriendInfo friendInfo : friendInfos) {
            String nutrCount = friendInfo.getNutrCount();
            if (!StrUtil.isBlank(nutrCount) && Integer.parseInt(nutrCount) >= 2) {
                friendInfosList.add(friendInfo);
            }
        }
        return friendInfosList;
    }

    /**
     * 领取产生的营养液
     * @param bubbleInfos
     * @throws JsonProcessingException
     */
    private void cultureBean(JsonNode bubbleInfos) throws JsonProcessingException, InterruptedException {
        log.info("{},开始领取帮助好友或做任务的营养液",userName);
        if (bubbleInfos == null) {
            log.info("{},无产生的营养液可领取",userName);
            return;
        }
        String jsonStr = bubbleInfos.toString();
        jsonStr.substring(1, jsonStr.length() - 1);

        List<BubbleInfos> bubbleInfoList = objectMapper.readValue(jsonStr, new TypeReference<List<BubbleInfos>>() {
        });



        if (bubbleInfoList == null || bubbleInfoList.size() == 0) {
            log.info("{},无产生的营养液可领取",userName);
            return;
        }

        CultureBeanPBIRequest cultureBeanPBIRequest = new CultureBeanPBIRequest(cookie);
        for (BubbleInfos infos : bubbleInfoList) {
            cultureBeanPBIRequest.setBody(currentRoundId, infos.getNutrientsType());
            String execute = cultureBeanPBIRequest.execute(restTemplate);
            log.info("{},产生营养液名称：{}，领取成功，返回：{}", userName,infos.getName(), execute);
        }
    }

    /*
     * @Author zyl
     * @Description 定时领取营养液
     * @Date 2021/1/15 17:17
     * @Param []
     * @return void
     **/
    private void receiveNutrients() throws JsonProcessingException {
        log.info("{},开始领取营养液", userName);
        ReceiveNutrientsPBIRequest receiveNutrientsPBIRequest = new ReceiveNutrientsPBIRequest();
        receiveNutrientsPBIRequest.setBody(currentRoundId);
        receiveNutrientsPBIRequest.setCookie(cookie);
        String execute = receiveNutrientsPBIRequest.execute(restTemplate);
        //格式化
        /**
         * 失败已经领过了
         * {"code":"0","data":{"nutrients":"5","state":"3","countDown":"10799","nextReceiveTime":"1610788873000","nutrCountLimit":"3","endTimeNutrCount":"3"}}
         * {"code":"0","errorCode":"PB501","errorMessage":"领取时间未到，不可领取"}
         * 成功
         *{"code":"0","data":{"nutrients":"4","state":"3","countDown":"10799","nextReceiveTime":"1610789129000","nutrCountLimit":"3","endTimeNutrCount":"3"}}
         */
        JsonNode receiveNutrients = objectMapper.readTree(execute);
        String code = receiveNutrients.get("code").asText();
        if (!"0".equals(code)) {
            //请求失败，参数可能出现错误
            log.warn("种豆得豆，领取自己营养液失败，返回：{}", execute);
        }
        JsonNode data = receiveNutrients.get("data");
        if (data == null) {
            //说明领取时间未到
            log.info("{},领取时间营养液未到，不可领取",userName);
            return;
        }
        //获取请求状态
        String nutrients = data.get("nutrients").asText();
        if ("4".equals(nutrients)) {
            log.info("{},领取成功，下次领取时间为：{}", userName,DateUtil.format(new Date(data.get("nextReceiveTime").asLong()), "yyyy-MM-dd HH:mm:ss"));
        } else if ("5".equals(nutrients)) {
            log.info("{},已被领取，下次领取时间为：{}", userName,DateUtil.format(new Date(data.get("nextReceiveTime").asLong()), "yyyy-MM-dd HH:mm:ss"));
        } else {
            log.info("{},种豆得豆，领取自己营养液失败,未知状态，返回json：{}", userName,data,toString());
        }
    }


}
