package cn.lucky.jdautotask.request;

import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.handle.nian.impl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class NianShouRequestTest {

    private RestTemplate restTemplate;

    private String cookie;

    @Before
    public void init(){
        restTemplate = RestTemplateConfig.getRestTemplateStringIsUtf8();
//        cookie = "pt_key=AAJf5w7aADAy8wXy7TrGhb6ico1jELCHQJTC7npw114j2KN2VB3EblC4297Hk5PKX433RhGRHDc;pt_pin=18337656372_p";
        cookie = "pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ";
    }

    @Test
    public void nianGetHomeDataRequestTest() throws InterruptedException {
        NianGetHomeDataRequest nianGetHomeDataRequest = new NianGetHomeDataRequest();
        nianGetHomeDataRequest.setCookie(cookie);
        String execute = nianGetHomeDataRequest.execute(restTemplate);

        System.out.println("execute = " + execute);

    }

    @Test
    public void nianCollectProduceScoreRequest() throws InterruptedException, JsonProcessingException {
        NianCollectProduceScoreRequest nianGetHomeDataRequest = new NianCollectProduceScoreRequest();
        nianGetHomeDataRequest.setBody("ckgWLGaEIbzZ7QymSEnlo4s");

        nianGetHomeDataRequest.setCookie(cookie);
        String execute = nianGetHomeDataRequest.execute(restTemplate);

        System.out.println("execute = " + execute);
    }


    @Test
    public void NianRaiseRequest() throws InterruptedException, JsonProcessingException {
        /**
         * execute = {"code":0,"data":{"bizCode":0,"result":{"activityInfo":{"activityEndTime":1613145599000,"activityStartTime":1610899200000,"firstAwardEndTime":1612627199000,"firstAwardStartTime":1612440000000,"firstWaitLotteryStartTime":1612436400000,"nowTime":1611216463065,"secondAwardStartTime":1613008800000,"secondWaitLotteryStartTime":1612958400000},"levelUp":1,"levelUpAward":{"brandCouponInfo":{"bannerImg":"https://img20.360buyimg.com/applylogo/jfs/t1/157434/40/3593/97885/60018265Eed79a870/4285564182af39d1.png","brandName":"欧莱雅男士","link":"https://pro.m.jd.com/mall/active/4K9bX7jJ727DPALxedMmAjeRhtrF/index.html","logoImg":"https://img30.360buyimg.com/applylogo/jfs/t1/161155/22/2930/5837/60018200E829692bf/de42e4cf3b7390c6.png","shopId":1000097062},"type":1},"raiseInfo":{"buttonStatus":1,"curLevelStartScore":"152000","fullFlag":false,"nextLevelScore":"237000","nextRed":3,"redNum":1,"remainScore":"19486","scoreLevel":4,"totalScore":"171486","usedScore":"152000"}},"success":true},"msg":"调用成功"}
         */
        NianRaiseRequest nianGetHomeDataRequest = new NianRaiseRequest();
        nianGetHomeDataRequest.setBody("ckgWLGaEIbzZ7QymSEnlo4s");

        nianGetHomeDataRequest.setCookie(cookie);
        String execute = nianGetHomeDataRequest.execute(restTemplate);

        System.out.println("execute = " + execute);

    }

    @Test
    public void NianKillCouponListRequest() throws InterruptedException, JsonProcessingException {
        /**
         * execute = {"code":0,"data":{"bizCode":0,"result":{"activityInfo":{"activityEndTime":1613145599000,"activityStartTime":1610899200000,"firstAwardEndTime":1612627199000,"firstAwardStartTime":1612440000000,"firstWaitLotteryStartTime":1612436400000,"nowTime":1611216463065,"secondAwardStartTime":1613008800000,"secondWaitLotteryStartTime":1612958400000},"levelUp":1,"levelUpAward":{"brandCouponInfo":{"bannerImg":"https://img20.360buyimg.com/applylogo/jfs/t1/157434/40/3593/97885/60018265Eed79a870/4285564182af39d1.png","brandName":"欧莱雅男士","link":"https://pro.m.jd.com/mall/active/4K9bX7jJ727DPALxedMmAjeRhtrF/index.html","logoImg":"https://img30.360buyimg.com/applylogo/jfs/t1/161155/22/2930/5837/60018200E829692bf/de42e4cf3b7390c6.png","shopId":1000097062},"type":1},"raiseInfo":{"buttonStatus":1,"curLevelStartScore":"152000","fullFlag":false,"nextLevelScore":"237000","nextRed":3,"redNum":1,"remainScore":"19486","scoreLevel":4,"totalScore":"171486","usedScore":"152000"}},"success":true},"msg":"调用成功"}
         */
        NianKillCouponListRequest request = new NianKillCouponListRequest();
        request.setCookie(cookie);
        String execute = request.execute(restTemplate);

        System.out.println("execute = " + execute);
    }


    /**
     * {"code":0,"data":{"bizCode":-1,"bizMsg":"已领取过","success":false},"msg":"调用成功"}
     * {"code":0,"data":{"bizCode":0,"bizMsg":"success","result":{"advertId":"0901445940","batchId":"766954034","couponLog":"https://m.360buyimg.com/babel/jfs/t1/161682/27/2882/4492/6001943aEa4d9a261/40fd416a58cde34d.png","groupId":"05138841","price":99,"productImg":"https://m.360buyimg.com/babel/jfs/t1/157358/28/3734/38865/6007d8f8Ef5fd9235/a31812d4ff82896d.png","productName":"欧乐B小炭管牙膏套装","quota":"30","realPrice":69,"score":"1000","skuId":100008864895,"status":1,"usageThreshold":"50"},"success":true},"msg":"调用成功"}
     *
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @Test
    public void NianKillCouponRequest() throws InterruptedException, JsonProcessingException {
        NianKillCouponRequest request = new NianKillCouponRequest();
        request.setCookie(cookie);
        request.setBody("KRR6TBPIeuiBvXCxdUPIowj1yA",100008864895L);
        System.out.println(request.getParam().get("body"));
        String execute = request.execute(restTemplate);
        System.out.println("execute = " + execute);
    }



}
