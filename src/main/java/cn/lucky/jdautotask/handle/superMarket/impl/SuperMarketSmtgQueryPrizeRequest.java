package cn.lucky.jdautotask.handle.superMarket.impl;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketPost;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * @Author zyl
 * @Description 查询东东超市蓝币可兑换商品
 * @Date 2021/1/22 14:01
 **/
public class SuperMarketSmtgQueryPrizeRequest extends AbstractRequestSuperMarketPost {

    public SuperMarketSmtgQueryPrizeRequest() {
        param.set("functionId", "smtg_queryPrize");
        setBodyByStr("%7B%7D");
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
