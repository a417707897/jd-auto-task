package cn.lucky.jdautotask.pojo.plantBeanIndex;

import lombok.Data;

/*
 * @Author zyl
 * @Description 商铺列表
 * @Date 2021/1/18 11:42
 **/
@Data
public class ShopTask {


    /**
     * shopTaskId : 20521
     * shopId : 37727
     * taskState : 2
     * shopName : 红蜻蜓官方旗舰店
     * shopLogo : https://img11.360buyimg.com/cms/jfs/t1/145844/22/1794/7424/5efbe66cEee1031cb/658dc6e48744fdbc.jpg
     * linkUrl : https://shop.m.jd.com/?shopId=37727
     */
    private String shopTaskId;
    private String shopId;
    private String taskState;
    private String shopName;
    private String shopLogo;
    private String linkUrl;
}
