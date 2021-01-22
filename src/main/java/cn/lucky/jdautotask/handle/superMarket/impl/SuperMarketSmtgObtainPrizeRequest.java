package cn.lucky.jdautotask.handle.superMarket.impl;


import cn.lucky.jdautotask.handle.superMarket.AbstractRequestSuperMarketPost;
import cn.lucky.jdautotask.utils.AssertUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * @Author zyl
 * @Description 兑换京豆 1000 or 1
 * @Date 2021/1/22 14:11
 **/
public class SuperMarketSmtgObtainPrizeRequest extends AbstractRequestSuperMarketPost {

    public SuperMarketSmtgObtainPrizeRequest() {
        setFunctionId("smtg_obtainPrize");
        httpHeaders.set("Accept-Encoding", "gzip, deflate, br");
        httpHeaders.set("Accept-Language", "zh-cn");
        httpHeaders.set("Connection", "keep-alive");
        httpHeaders.set("Accept", "application/json, text/plain, */*");
    }

    public void setBody(String prizeId) {
        AssertUtil.strNotNull(prizeId, "商品id不为空");
        setBodyByStr("%7B%22prizeId%22:%22"+prizeId+"%22%7D");
    }

    @Override
    protected void checkParam() throws JsonProcessingException {
    }
}
