package cn.lucky.jdautotask.handle.farm;

import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.farm.dailyTasks.FarmBrowseAdTaskForFarmRequest;
import cn.lucky.jdautotask.handle.farm.dailyTasks.FarmGetFullCollectionRewardRequest;
import cn.lucky.jdautotask.handle.farm.dailyTasks.FarmInitForFarmRequest;
import cn.lucky.jdautotask.pojo.farm.Themes;
import cn.lucky.jdautotask.pojo.farm.userBrowseTaskAds;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 东东农场日常任务
 */
@Log4j2
public class FarmDailyTasksHandle extends AbstractJdAutoTaskHandle {

    private FarmCommonRequestGet farmCommonRequestGet = null;

    private String cookie;



    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) throws InterruptedException {

        cookie = jdAutoTaskRequest.getCookie();
        //获取东东农场首页信息
        try {
            FarmInitForFarmRequest farmInitForFarmRequest = new FarmInitForFarmRequest();
            farmInitForFarmRequest.setCookie(cookie);
            String execute = farmInitForFarmRequest.execute(restTemplate);
            JsonNode farmInitForFarm = objectMapper.readTree(execute);
            if (!checkResponseOnlyCode(farmInitForFarm)) {
                log.warn("东东农场首页信息获取失败");
                return;
            }
            //用户信息
            JsonNode farmUserPro = farmInitForFarm.get("farmUserPro");
            if (farmUserPro == null) {
                log.warn("东东用户信息获取失败");
                return;
            }
            log.info("-------------------------当前农场商品状态-------------------------------");
            if (farmInitForFarm.get("treeState").asInt() == 2 || farmInitForFarm.get("treeState").asInt() == 3) {
                log.info("东东农场商品名称：{}，现在可被领取", farmUserPro.get("name").asText());
                return;
            } else if (farmInitForFarm.get("treeState").asInt() == 1) {
                log.info("东东农场商品名称：{}，还在种植中。。。", farmUserPro.get("name").asText());
            } else {
                log.info("东东农场商品，未被种植");
                return;
            }
            log.info("东东农场商品名称：{}", farmUserPro.get("name").asText());
            log.info("东东农场互助码：{}", farmUserPro.get("shareCode").asText());
            log.info("东东农场已成功兑换水果：{}", farmUserPro.get("winTimes").asText());
            log.info("----------------------------------------------------------------------");
            //查看是否被水滴砸中，可领取水滴
            if (farmInitForFarm.get("todayGotWaterGoalTask") != null
                    && farmInitForFarm.get("todayGotWaterGoalTask").get("canPop").asBoolean()
            ) {
                log.info("被水滴砸中，开始领取水滴");
                farmCommonRequestGet = new FarmCommonRequestGet("gotWaterGoalTaskForFarm",
                        "{type: 3}");
                farmCommonRequestGet.setCookie(cookie);
                String execute1 = farmCommonRequestGet.execute(restTemplate);
                JsonNode gotWaterGoalTaskForFarm = objectMapper.readTree(execute1);
                if (checkResponseOnlyCode(gotWaterGoalTaskForFarm)) {
                    log.info("【被水滴砸中】领取：{}个", gotWaterGoalTaskForFarm.get("addEnergy").asText());
                } else {
                    log.warn("【被水滴砸中】请求失败");
                }
            } else {
                log.warn("【未水滴砸中】领取不了水滴哦");
            }

            //获取任务列表 Or 初始化任务列表
            farmCommonRequestGet = new FarmCommonRequestGet("taskInitForFarm");
            farmCommonRequestGet.setCookie(cookie);
            String execute1 = farmCommonRequestGet.execute(restTemplate);
            JsonNode taskInitForFarm = objectMapper.readTree(execute1);
            if (!checkResponseOnlyCode(taskInitForFarm)
            ) {
                log.warn("任务列表初始化、获取失败");
                return;
            }
            //查看是否签到
            if (!taskInitForFarm.get("signInit").get("todaySigned").asBoolean()) {
                log.info("东东农场未签到，开始签到");
                farmCommonRequestGet = new FarmCommonRequestGet("signForFarm");
                farmCommonRequestGet.setCookie(cookie);
                String execute2 = farmCommonRequestGet.execute(restTemplate);
                JsonNode signForFarm = objectMapper.readTree(execute2);
                if (!checkResponseOnlyCode(signForFarm)) {
                    log.warn("东东农场签到失败");
                } else {
                    log.info("东东农场签到成功，获得：{}", signForFarm.get("amount").asText());
                }
            } else {
                log.info("东东农场今日已签到");
            }

            log.info("开始做广告浏览业务");
            if (!taskInitForFarm.get("gotBrowseTaskAdInit").get("f").asBoolean()) {
                List<userBrowseTaskAds> userBrowseTaskAdsList = objectMapper.readValue(
                        JsonFormatUtil.formatJsonNodeToStr(taskInitForFarm.get("gotBrowseTaskAdInit").get("userBrowseTaskAds")),
                        new TypeReference<List<userBrowseTaskAds>>() {
                        }
                );
                FarmBrowseAdTaskForFarmRequest farmBrowseAdTaskForFarmRequest = null;

                for (userBrowseTaskAds userBrowseTaskAd : userBrowseTaskAdsList) {
                    farmBrowseAdTaskForFarmRequest = new FarmBrowseAdTaskForFarmRequest();
                    farmBrowseAdTaskForFarmRequest.setBrowseBody(userBrowseTaskAd.getAdvertId());
                    farmBrowseAdTaskForFarmRequest.setCookie(cookie);
                    String execute2 = farmBrowseAdTaskForFarmRequest.execute(restTemplate);
                    JsonNode browseAdTaskForFarm = objectMapper.readTree(execute2);
                    if (checkResponseOnlyCode(browseAdTaskForFarm)) {
                        log.info("浏览：{}任务已经完成，下面开始领取奖励", userBrowseTaskAd.getMainTitle());
                    } else {
                        log.info("浏览：{}任务已经失败，返回json：{}", userBrowseTaskAd.getMainTitle(), execute2);
                        continue;
                    }

                    farmBrowseAdTaskForFarmRequest = new FarmBrowseAdTaskForFarmRequest();
                    farmBrowseAdTaskForFarmRequest.setReceiveBody(userBrowseTaskAd.getAdvertId());
                    farmBrowseAdTaskForFarmRequest.setCookie(cookie);
                    String execute3 = farmBrowseAdTaskForFarmRequest.execute(restTemplate);
                    browseAdTaskForFarm = objectMapper.readTree(execute3);

                    if (checkResponseOnlyCode(browseAdTaskForFarm)) {
                        log.info("任务：{}，成功领取奖励，获得：{}", userBrowseTaskAd.getMainTitle(), browseAdTaskForFarm.get("amount"));
                    } else {
                        log.info("任务：{}，领取奖励失败，返回json：{}", userBrowseTaskAd.getMainTitle(), execute2);
                    }
                }
            } else {
                log.info("今天已经做过浏览广告任务");
            }

            //定时领水
            log.info("开始做定时领水业务");
            if (!taskInitForFarm.get("gotThreeMealInit").get("f").asBoolean()) {
                farmCommonRequestGet = new FarmCommonRequestGet("gotThreeMealForFarm");
                farmCommonRequestGet.setCookie(cookie);
                String execute2 = farmCommonRequestGet.execute(restTemplate);
                JsonNode gotThreeMealForFarm = objectMapper.readTree(execute2);
                if (!checkResponseOnlyCode(gotThreeMealForFarm)) {
                    log.info("【定时领水】成功，获得：{}", gotThreeMealForFarm.get("amount").asText());
                } else {
                    log.info("【定时领水】失败，返回json：{}", execute2);
                }
            } else {
                log.info("当前不在定时领水时间段或者已经领过");
            }

            log.info("开始打卡领水活动");
            farmCommonRequestGet = new FarmCommonRequestGet("clockInInitForFarm");
            farmCommonRequestGet.setCookie(cookie);
            String execute2 = farmCommonRequestGet.execute(restTemplate);
            JsonNode clockInInitForFarm = objectMapper.readTree(execute2);
            if (checkResponseOnlyCode(clockInInitForFarm)) {
                if (!clockInInitForFarm.get("todaySigned").asBoolean()) {
                    log.info("开始今日签到");
                    farmCommonRequestGet = new FarmCommonRequestGet("clockInForFarm", "{\"type\": 1}");
                    farmCommonRequestGet.setCookie(cookie);
                    String execute3 = farmCommonRequestGet.execute(restTemplate);
                    JsonNode clockInForFarm = objectMapper.readTree(execute3);
                    if (checkResponseOnlyCode(clockInForFarm)) {
                        log.info("打卡成功，第{}天签到，获得：{}", clockInForFarm.get("signDay").asText(), clockInForFarm.get("amount").asText());
                        //判断是不是签到7天，然后领取水滴
                        if ("7".equals(clockInForFarm.get("signDay").asText())) {
                            log.info("已签到7天,开始领取--惊喜礼包38g水滴");
                            farmCommonRequestGet = new FarmCommonRequestGet("clockInForFarm", "{\"type\": 2}");
                            farmCommonRequestGet.setCookie(cookie);
                            String execute4 = farmCommonRequestGet.execute(restTemplate);
                            JsonNode clockInForFarm2 = objectMapper.readTree(execute4);
                            if (checkResponseOnlyCode(clockInForFarm2)) {
                                log.info("【惊喜礼包】获得：{}", clockInForFarm2.get("amount").asText());
                            } else {
                                log.warn("【惊喜礼包】领取失败");
                            }
                        }
                    } else {
                        log.warn("打卡签到失败，返回json：{}", execute3);
                    }
                } else {
                    log.info("今日已签到");
                }

                //限时关注的水滴
                log.info("开始限时关注的水滴活动");
                if (clockInInitForFarm.get("themes") != null &&
                        clockInInitForFarm.get("themes").size() > 0
                ) {
                    List<Themes> themes = objectMapper.readValue(JsonFormatUtil.formatJsonNodeToStr(clockInInitForFarm.get("themes")),
                            new TypeReference<List<Themes>>() {
                            });
                    Map<String, Object> bodyMap = new HashMap<>();
                    for (Themes theme : themes) {
                        if (!theme.getHadGot()) {
                            log.info("开始关注商品id：{}，名称：{}", theme.getId(), theme.getName());
                            farmCommonRequestGet = new FarmCommonRequestGet("clockInFollowForFarm");
                            farmCommonRequestGet.setCookie(cookie);
                            bodyMap.put("id", theme.getId());
                            bodyMap.put("type", "theme");
                            bodyMap.put("step", 1);
                            farmCommonRequestGet.setBodyByValueObject(bodyMap);

                            String execute3 = farmCommonRequestGet.execute(restTemplate);
                            JsonNode clockInFollowForFarm = objectMapper.readTree(execute3);
                            if (checkResponseOnlyCode(clockInFollowForFarm)) {
                                farmCommonRequestGet = new FarmCommonRequestGet("clockInFollowForFarm");
                                farmCommonRequestGet.setCookie(cookie);
                                bodyMap.put("step", 2);
                                farmCommonRequestGet.setBodyByValueObject(bodyMap);
                                String execute4 = farmCommonRequestGet.execute(restTemplate);
                                clockInFollowForFarm = objectMapper.readTree(execute4);
                                if (checkResponseOnlyCode(clockInFollowForFarm)) {
                                    log.info("关注：{}成功，获取水滴：{}", theme.getName(), clockInFollowForFarm.get("amount").asText());
                                }
                            }
                        } else {
                            log.info("商品id：{}，名称：{}，已完成关注", theme.getId(), theme.getName());
                        }
                    }
                }
            } else {
                log.warn("打卡领水活动请求失败");
            }

            log.info("开始水滴雨任务，每天两次，最多可得10g水滴");
            if (taskInitForFarm.get("waterRainInit") != null && !taskInitForFarm.get("waterRainInit").get("f").asBoolean()) {
                if (taskInitForFarm.get("waterRainInit").get("lastTime") != null &&
                        (taskInitForFarm.get("waterRainInit").get("lastTime").asLong()+3 * 60 * 60 * 1000L) <
                                new Date().getTime()
                ) {
                    log.info("开始水滴雨任务,这是第：{}次", taskInitForFarm.get("waterRainInit").get("winTimes").asInt() + 1);
                    farmCommonRequestGet = new FarmCommonRequestGet("waterRainForFarm"
                            , "{\"type\": 1, \"hongBaoTimes\": 100, \"version\": 3}"
                    );

                    farmCommonRequestGet.setCookie(cookie);
                    String execute3 = farmCommonRequestGet.execute(restTemplate);
                    JsonNode waterRainForFarm = objectMapper.readTree(execute3);
                    if (checkResponseOnlyCode(waterRainForFarm)) {
                        log.info("水滴雨任务执行成功，获取水滴：{}", waterRainForFarm.get("addEnergy").asInt());
                    } else {
                        log.info("水滴雨任务执行失败，返回json：{}", execute3);
                    }
                } else {
                    log.info("水滴雨任务失败，时间可能未到");
                }
            } else {
                log.warn("水滴雨任务已经完成");
            }

            log.info("开始浇水十次任务");


            if (taskInitForFarm.get("totalWaterTaskInit") != null) {
                Integer totalWaterTaskTimes = taskInitForFarm.get("totalWaterTaskInit").get("totalWaterTaskTimes").asInt();
                Integer totalWaterTaskLimit = taskInitForFarm.get("totalWaterTaskInit").get("totalWaterTaskLimit").asInt();

                if (totalWaterTaskTimes < totalWaterTaskLimit) {
                    log.info("准备浇水：{}次", totalWaterTaskLimit - totalWaterTaskTimes);
                    for (int i = 0; i < totalWaterTaskLimit - totalWaterTaskTimes; i++) {
                        farmCommonRequestGet = new FarmCommonRequestGet("waterGoodForFarm");
                        farmCommonRequestGet.setCookie(cookie);
                        String execute3 = farmCommonRequestGet.execute(restTemplate);
                        JsonNode waterGoodForFarm = objectMapper.readTree(execute3);
                        if (checkResponseOnlyCode(waterGoodForFarm)) {
                            log.info("浇水成功，剩下水滴：{}", waterGoodForFarm.get("totalEnergy").asInt());
                            //领取水滴首次浇水奖励
                            if (totalWaterTaskTimes == 0 && i == 0) {
                                farmCommonRequestGet = new FarmCommonRequestGet("firstWaterTaskForFarm");
                                farmCommonRequestGet.setCookie(cookie);
                                String execute4 = farmCommonRequestGet.execute(restTemplate);
                                JsonNode firstWaterTaskForFarm = objectMapper.readTree(execute4);

                                if (checkResponseOnlyCode(firstWaterTaskForFarm)) {
                                    log.info("首次浇水奖励领取成功，获得：{}", firstWaterTaskForFarm.get("amount").asInt());
                                } else {
                                    log.info("首次浇水奖励领取失败");
                                }
                            }
                            //如果浇水等于10次，那就领取10次奖励
                            if (i + 1 == totalWaterTaskLimit - totalWaterTaskTimes) {
                                farmCommonRequestGet = new FarmCommonRequestGet("totalWaterTaskForFarm");
                                farmCommonRequestGet.setCookie(cookie);
                                String execute4 = farmCommonRequestGet.execute(restTemplate);
                                JsonNode firstWaterTaskForFarm = objectMapper.readTree(execute4);

                                if (checkResponseOnlyCode(firstWaterTaskForFarm)) {
                                    log.info("十次浇水奖励领取成功，获得：{}", firstWaterTaskForFarm.get("totalWaterTaskEnergy").asInt());
                                } else {
                                    log.info("十次浇水奖励领取失败");
                                }
                            }


                            //这里加个判断
                            if (waterGoodForFarm.get("finished").asBoolean()) {
                                log.info("水果可以去领取兑换了");
                                break;
                            }
                            if (waterGoodForFarm.get("totalEnergy").asInt() < 10) {
                                log.info("水滴不够浇水了");
                                break;
                            }
                        }
                    }
                }
            }

            log.info("开始小鸭子游戏");
            FarmGetFullCollectionRewardRequest farmGetFullCollectionRewardRequest = null;
            for (int i = 0; i < 10; i++) {
                farmGetFullCollectionRewardRequest = new FarmGetFullCollectionRewardRequest();

                farmGetFullCollectionRewardRequest.setCookie(cookie);
                String execute3 = farmGetFullCollectionRewardRequest.execute(restTemplate);
                JsonNode getFullCollectionReward = objectMapper.readTree(execute3);
                if (checkResponseOnlyCode(getFullCollectionReward)) {
                    log.info("小鸭子游戏{}", getFullCollectionReward.get("title").asText());
                    if (getFullCollectionReward.get("hasLimit").asBoolean()) {
                        break;
                    }
                }

                if (getFullCollectionReward.get("code").asText().equals("10")) {
                    log.info("小鸭子游戏达到上限");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
