package cn.lucky.jdautotask.handle.superMarket;

import cn.hutool.core.util.StrUtil;
import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.superMarket.exchange.SuperMarketSmtgNewHomeRequest;
import cn.lucky.jdautotask.handle.superMarket.other.SuperMarketSmtgReceiveCoinRequest;
import cn.lucky.jdautotask.handle.superMarket.shelf.SuperMarketSmtgSellMerchandiseRequest;
import cn.lucky.jdautotask.handle.superMarket.shelf.SuperMarketSmtgShelfUnlockRequest;
import cn.lucky.jdautotask.handle.superMarket.shelf.SuperMarketSmtgShelfUpgradeRequest;
import cn.lucky.jdautotask.handle.superMarket.shelf.SuperMarketSmtgShopIndexRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.pojo.superMarket.Shelf;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import sun.dc.pr.PRError;

import java.util.List;


/**
 * 定时处理东东超市任务
 */
@Log4j2
public class SuperMarketTimingHandle extends AbstractJdAutoTaskHandle {

    private String cookie;

    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) throws InterruptedException {
        cookie = jdAutoTaskRequest.getCookie();

        try {
            //领取营业额
            SuperMarketSmtgReceiveCoinRequest superMarketSmtgReceiveCoinRequest = new SuperMarketSmtgReceiveCoinRequest();
            superMarketSmtgReceiveCoinRequest.setBodyByReceiveTurnover();
            superMarketSmtgReceiveCoinRequest.setCookie(cookie);
            String execute = superMarketSmtgReceiveCoinRequest.execute(restTemplate);
            if (checkResponseToBiz(objectMapper.readTree(execute))) {
                log.info("领取东东超市营业额成功");
            } else {
                log.info("领取东东超市营业额失败");
            }
            //查询货架信息
            SuperMarketSmtgShopIndexRequest superMarketSmtgShopIndexRequest = new SuperMarketSmtgShopIndexRequest();
            superMarketSmtgShopIndexRequest.setCookie(cookie);
            String execute1 = superMarketSmtgShopIndexRequest.execute(restTemplate);
            JsonNode jsonNode = objectMapper.readTree(execute1);
            if (!checkResponseToBiz(jsonNode)) {
                log.warn("东东超市货架信息请求失败，返回json：{}", execute1);
                return;
            }

            JsonNode shelfListJson = jsonNode.get("data").get("result").get("shelfList");
            List<Shelf> shelves = objectMapper.readValue(JsonFormatUtil.formatJsonNodeToStr(shelfListJson),
                    new TypeReference<List<Shelf>>() {
                    });
            if (shelves == null || shelves.size() == 0) {
                log.warn("东东超市无货架信息");
                return;
            }
            String shopId = jsonNode.get("data").get("result").get("shopId").asText();

            for (Shelf shelf : shelves) {
                if (shelf.getStatus() == 0) {
                    log.info("当前货架：【{}】已解锁，当前等级：{}", shelf.getName(), shelf.getLevel());
                } else if (shelf.getStatus() == -1) {
                    log.info("当前货架：【{}】未解锁", shelf.getName());
                } else if (shelf.getStatus() == 1) {
                    log.info("当前货架：【{}】可升级", shelf.getName());
                    SuperMarketSmtgShelfUpgradeRequest superMarketSmtgShelfUpgradeRequest
                            = new SuperMarketSmtgShelfUpgradeRequest();
                    superMarketSmtgShelfUpgradeRequest.setCookie(cookie);
                    superMarketSmtgShelfUpgradeRequest.setBody(shopId, shelf.getId(), shelf.getLevel());
                    String execute2 = superMarketSmtgShelfUpgradeRequest.execute(restTemplate);
                    if (checkResponseToBiz(objectMapper.readTree(execute2))) {
                        log.info("货架：【{}】升级成功", shelf.getName());
                    } else {
                        log.info("货架：【{}】升级失败，返回json：{}", shelf.getName(), execute2);
                    }
                } else if (shelf.getStatus() == 2) {
                    log.info("当前货架：【{}】可解锁", shelf.getName());
                    SuperMarketSmtgShelfUnlockRequest superMarketSmtgShelfUnlockRequest = new SuperMarketSmtgShelfUnlockRequest();
                    superMarketSmtgShelfUnlockRequest.setCookie(cookie);
                    superMarketSmtgShelfUnlockRequest.setBody(shopId, shelf.getId());
                    String execute2 = superMarketSmtgShelfUnlockRequest.execute(restTemplate);
                    if (checkResponseToBiz(objectMapper.readTree(execute2))) {
                        log.info("货架：【{}】解锁成功", shelf.getName());
                    } else {
                        log.info("货架：【{}】解锁失败，返回json：{}", shelf.getName(), execute2);
                    }
                } else {
                    log.info("当前货架：【{}】未知的状态：{}", shelf.getName(), shelf.getStatus());
                }
            }

            //售卖限时物品
            JsonNode forSaleMerchandise = jsonNode.get("data").get("result").get("forSaleMerchandise");
            if (forSaleMerchandise == null || forSaleMerchandise.size() == 0) {
                log.info("货架无在售限时物品");
            } else {
                for (JsonNode node : forSaleMerchandise) {
                    log.info("货架，限时物品：{}，已上架", node.get("name").asText());
                }
            }

            JsonNode merchandiseList = jsonNode.get("data").get("result").get("merchandiseList");
            if (merchandiseList == null || merchandiseList.size() == 0) {
                log.info("货架无可售限时物品");
            } else {
                //开始销售限时物品
                SuperMarketSmtgSellMerchandiseRequest superMarketSmtgSellMerchandiseRequest = new SuperMarketSmtgSellMerchandiseRequest();
                superMarketSmtgReceiveCoinRequest.setCookie(cookie);
                for (JsonNode node : merchandiseList) {
                    superMarketSmtgSellMerchandiseRequest.setBody(shopId, node.get("id").asText());
                    String execute2 = superMarketSmtgSellMerchandiseRequest.execute(restTemplate);
                    if (checkResponseToBiz(objectMapper.readTree(execute2))) {
                        log.info("限时物品销售完成");
                    } else {
                        log.info("限时物品销售失败");
                    }
                }
            }


            //领取升级店铺蓝币奖励
            SuperMarketSmtgNewHomeRequest superMarketSmtgNewHomeRequest = new SuperMarketSmtgNewHomeRequest();
            superMarketSmtgNewHomeRequest.setCookie(cookie);
            String execute3 = superMarketSmtgNewHomeRequest.execute(restTemplate);
            JsonNode smtgNewHomeJson = objectMapper.readTree(execute3);
            if (!checkResponseToBiz(smtgNewHomeJson)) {
                log.warn("东东超市新店铺首页信息获取失败，返回json：{}", execute3);
                return;
            }

            //获取信息
            JsonNode userUpgradeBlueVos = smtgNewHomeJson.get("data").get("result").get("userUpgradeBlueVos");
            if (userUpgradeBlueVos == null || userUpgradeBlueVos.size() == 0) {
                log.warn("可领取的店铺蓝币奖励为0");
                return;
            }
            for (JsonNode userUpgradeBlueVo : userUpgradeBlueVos) {
                superMarketSmtgReceiveCoinRequest = new SuperMarketSmtgReceiveCoinRequest();
                superMarketSmtgReceiveCoinRequest.setCookie(cookie);

                superMarketSmtgReceiveCoinRequest.setBodyByReceiveUpgrade(userUpgradeBlueVo.get("id").asText());
                String execute2 = superMarketSmtgReceiveCoinRequest.execute(restTemplate);
                if (checkResponseToBiz(objectMapper.readTree(execute2))) {
                    log.info("升级店铺蓝币奖励领取成功");
                } else {
                    log.info("升级店铺蓝币奖励领取失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
