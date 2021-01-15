package cn.lucky.jdautotask.pojo.plantBeanIndex;

import lombok.Data;

import java.util.List;

@Data
public class Round {


    /**
     * roundId : pqs77fcey75xkoqbns6eertieu
     * roundState : 2
     * headImgUrl : https://m.360buyimg.com/wutong/jfs/t1/152500/17/12700/17725/5fedc3b2E1bddcb02/60fdd2361cd65512.png
     * awardState : 1
     * beanState : 4
     * growth : 119
     * nutrients : 1
     * redDot : 2
     * bubbleInfos : [{"nutrientsType":"13","name":"好友帮收","nutrNum":1}]
     * splitBeans : 180000000
     * splitBeansDesc : 1.8亿
     * dateDesc : 本期 1月11日-1月17日
     * beanImg : https://m.360buyimg.com/babel/jfs/t1/116429/19/11902/498078/5f053c2aE9cc6b0cd/0e4c91c162f40bf6.gif
     */

    private String roundId;
    private String roundState;
    private String headImgUrl;
    private String awardState;
    private String beanState;
    private String growth;
    private String nutrients;
    private String redDot;
    private String splitBeans;
    private String splitBeansDesc;
    private String dateDesc;
    private String beanImg;
    private List<BubbleInfos> bubbleInfos;


}
