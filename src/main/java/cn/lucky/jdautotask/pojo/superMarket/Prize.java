package cn.lucky.jdautotask.pojo.superMarket;

import lombok.Data;

@Data
public class Prize {


    /**
     * beanType :
     * blueCost : 800000
     * couponLink : https://so.m.jd.com/list/couponSearch.action?ptag=37070.3.2&couponbatch=766951818
     * discount : 78.9
     * finishNum : 0
     * icon : https://m.360buyimg.com/babel/jfs/t1/161888/19/2683/82167/5fffe7f1E5ede0faf/77819b702d9e93b6.jpg
     * inStock : 506
     * prizeId : 6801443593
     * status : 1
     * subTitle : 满79-78.9
     * targetNum : 1
     * threshold : 79
     * title : 纯甄芒果芝士味礼盒装
     * type : 2
     */

    private Integer beanNum;
    private String beanType;
    private Long blueCost;
    private String couponLink;
    private String discount;
    private Integer finishNum;
    private String icon;
    private Integer inStock;
    private String prizeId;
    private Integer status;
    private String subTitle;
    private Integer targetNum;
    private String threshold;
    private String title;
    private Integer type;
}
