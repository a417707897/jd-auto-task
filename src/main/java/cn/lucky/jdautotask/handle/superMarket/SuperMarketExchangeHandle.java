package cn.lucky.jdautotask.handle.superMarket;

import cn.hutool.core.util.StrUtil;
import cn.lucky.jdautotask.handle.common.AbstractJdAutoTaskHandle;
import cn.lucky.jdautotask.handle.superMarket.impl.SuperMarketSmtgHomeRequest;
import cn.lucky.jdautotask.handle.superMarket.impl.SuperMarketSmtgObtainPrizeRequest;
import cn.lucky.jdautotask.handle.superMarket.impl.SuperMarketSmtgQueryPrizeRequest;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import cn.lucky.jdautotask.pojo.superMarket.Prize;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/*
 * @Author zyl
 * @Description 东东超市兑换商品，目前只兑换1000京豆和万能京豆
 * @Date 2021/1/22 14:20
 **/
@Log4j2
public class SuperMarketExchangeHandle extends AbstractJdAutoTaskHandle {

    private String cookie;

    //蓝币
    private Long totalBlue;

    //金币
    private Long totalGold;


    @Override
    public void doExecute(JdAutoTaskRequest jdAutoTaskRequest) throws InterruptedException {

        cookie = jdAutoTaskRequest.getCookie();

        try {
            //获取首页数据，获取兑换蓝币
            SuperMarketSmtgHomeRequest superMarketSmtgHomeRequest = new SuperMarketSmtgHomeRequest();
            superMarketSmtgHomeRequest.setCookie(cookie);
            String execute = superMarketSmtgHomeRequest.execute(restTemplate);
            JsonNode jsonNode = objectMapper.readTree(execute);

            if (jsonNode.get("code") == null ||
                    !"0".equals(jsonNode.get("code").asText()) ||
                    jsonNode.get("data") == null ||
                    jsonNode.get("data").get("bizCode") == null ||
                    !"0".equals(jsonNode.get("data").get("bizCode").asText())
            ) {
                log.warn("东东超市首页信息查询失败，返回json：{}", execute);
                return;
            }
            totalBlue = jsonNode.get("data").get("result").get("totalBlue").asLong();
            totalGold = jsonNode.get("data").get("result").get("totalGold").asLong();
            log.info("首页信息查询完成，金币数量：{}，蓝币数量：{}", totalGold, totalBlue);
            //查询可兑换的商品id
            SuperMarketSmtgQueryPrizeRequest superMarketSmtgQueryPrizeRequest = new SuperMarketSmtgQueryPrizeRequest();
            superMarketSmtgQueryPrizeRequest.setCookie(cookie);
            String smtgQueryPrize = superMarketSmtgQueryPrizeRequest.execute(restTemplate);
            JsonNode smtgQueryPrizeJsonNode = objectMapper.readTree(smtgQueryPrize);
            if (smtgQueryPrizeJsonNode.get("code") == null ||
                    !"0".equals(smtgQueryPrizeJsonNode.get("code").asText()) ||
                    smtgQueryPrizeJsonNode.get("data") == null ||
                    smtgQueryPrizeJsonNode.get("data").get("bizCode") == null ||
                    !"0".equals(smtgQueryPrizeJsonNode.get("data").get("bizCode").asText())
            ) {
                log.warn("东东超市可兑换商品列表查询失败，返回json：{}", execute);
                return;
            }
            List<Prize> prizeList = objectMapper.readValue(JsonFormatUtil.formatJsonNodeToStr(smtgQueryPrizeJsonNode.get("data").get("result").get("prizeList")),
                    new TypeReference<List<Prize>>() {
                    }
            );

            if (prizeList == null || prizeList.size() == 0) {
                log.warn("可兑换的商品列表为空");
                return;
            }
            SuperMarketSmtgObtainPrizeRequest superMarketSmtgObtainPrizeRequest = null;
            //我们只兑换万能京豆和1000京豆，他们的beanType是 BeanPackage Bean
            for (Prize prize : prizeList) {
                if (506==prize.getInStock()) {
                    log.warn("商品：{}，已经被兑换完", prize.getTitle());
                    continue;
                } else if ("BeanPackage".equals(prize.getBeanType()) || "Bean".equals(prize.getBeanType())) {
                    //商品id不能为空
                    if (StrUtil.isBlank(prize.getPrizeId())) {
                        log.warn("兑换商品：{}失败，商品id为空", prize.getTitle());
                        continue;
                    }
                    //判断蓝币是否足够
                    if (totalBlue >= prize.getBlueCost()) {
                        //直接开始兑换
                        superMarketSmtgObtainPrizeRequest = new SuperMarketSmtgObtainPrizeRequest();
                        superMarketSmtgObtainPrizeRequest.setBody(prize.getPrizeId());
                        superMarketSmtgObtainPrizeRequest.setCookie(cookie);
                        String smtgObtainPrize = superMarketSmtgObtainPrizeRequest.execute(restTemplate);
                        JsonNode smtgObtainPrizeJson = objectMapper.readTree(smtgObtainPrize);

                        if (smtgObtainPrizeJson.get("code") == null ||
                                !"0".equals(smtgObtainPrizeJson.get("code").asText()) ||
                                smtgObtainPrizeJson.get("data") == null ||
                                smtgObtainPrizeJson.get("data").get("bizCode") == null
                        ) {
                            log.warn("东东超市兑换手机接口出错，返回json：{}", execute);
                            return;
                        }

                        String bizCode = smtgObtainPrizeJson.get("data").get("bizCode").asText();
                        if ("0".equals(bizCode)) {
                            log.info("商品：{}，兑换成功", prize.getTitle());
                        } else {
                            log.info("商品：{}，兑换失败，返回结果：{}", prize.getTitle(),smtgObtainPrizeJson.get("data").get("bizMsg").asText());
                        }
                    }
                } else {
                    log.warn("商品：{}，无兑换分支", prize.getTitle());
                }
            }
        } catch (Exception e) {
            log.warn("兑换京豆出现异常");
            e.printStackTrace();
        }
    }
}
