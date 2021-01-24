package cn.lucky.jdautotask.handle.farm;

import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.farm.dailyTasks.FarmInitForFarmRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;

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
            JsonNode jsonNode = objectMapper.readTree(execute);
            if (!checkResponseOblyCode(jsonNode)) {
                log.warn("东东农场首页信息获取失败");
                return;
            }
            //用户信息
            JsonNode farmUserPro = jsonNode.get("farmUserPro");
            if (farmUserPro == null) {
                log.warn("东东用户信息获取失败");
                return;
            }
            log.info("-------------------------当前农场商品状态-------------------------------");
            if (jsonNode.get("treeState").asInt()==2 || jsonNode.get("treeState").asInt()==3) {
                log.info("东东农场商品名称：{}，现在可被领取",farmUserPro.get("name").asText());
                return;
            } else if (jsonNode.get("treeState").asInt() == 1) {
                log.info("东东农场商品名称：{}，还在种植中。。。", farmUserPro.get("name").asText());
            } else {
                log.info("东东农场商品，未被种植");
                return;
            }
            log.info("东东农场商品名称：{}",farmUserPro.get("name").asText());
            log.info("东东农场互助码：{}",farmUserPro.get("shareCode").asText());
            log.info("东东农场已成功兑换水果：{}",farmUserPro.get("winTimes").asText());
            log.info("----------------------------------------------------------------------");




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
