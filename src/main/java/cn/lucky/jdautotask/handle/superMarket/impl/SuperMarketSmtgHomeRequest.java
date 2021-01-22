package cn.lucky.jdautotask.handle.superMarket.impl;

import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketPost;
import com.fasterxml.jackson.core.JsonProcessingException;


/*
 * @Author zyl
 * @Description 查询东东超市首页信息
 * @Date 2021/1/22 13:58
 **/
public class SuperMarketSmtgHomeRequest extends AbstractRequestSuperMarketPost {

    public SuperMarketSmtgHomeRequest() {
        param.set("functionId", "smtg_home");
    }


    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
