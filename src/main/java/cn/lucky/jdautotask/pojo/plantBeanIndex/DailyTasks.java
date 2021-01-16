package cn.lucky.jdautotask.pojo.plantBeanIndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DailyTasks {


    /**
     * id : 308
     * taskName : 逛逛会场
     * taskNo : 2
     * taskType : 4
     * startTime : 1610294400000
     * endTime : 1610899199000
     * awardType : 4
     * iconUrl : https://m.360buyimg.com/wutong/jfs/t1/162449/16/1758/15828/5ff83df8E437def0a/368fd9d95e78def4.png
     * desc : 逛食品会场可得1瓶 购物次日可得30瓶
     * btnTxt : 去购物
     * btnLink : https://pro.m.jd.com/mall/active/UEqwNWVvi4SfQSXPoiigPfTKfcp/index.html
     * isPopup : 2
     * isNavIntercept : 2
     * totalNum : 1
     * gainedNum : 0
     * isFinished : 0
     * awardNumber : 1
     * dailyTimes : 1
     * productName : 逛逛会场
     * isReceiveNutrients : 1
     * priceRate : 100
     * isHidden : 2
     * prizeConf : 
     * popupText : 浏览逛逛会场即可获得1瓶营养液，逛逛会场次日12点前可得1瓶营养液！
     * client : 1,2,3
     * version : 
     * mpSpecial : 0
     * otherConf : 2
     * iconUrlNew : https://m.360buyimg.com/wutong/jfs/t1/169292/19/1742/13191/5ff83e12Ed32af793/3b4d8d893360f9da.png
     * isDmp : 2
     */

    private Integer id;
    private String taskName;
    private Integer taskNo;
    private Integer taskType;
    private Long startTime;
    private Long endTime;
    private Integer awardType;
    private String iconUrl;
    private String desc;
    private String btnTxt;
    private String btnLink;
    private Integer isPopup;
    private Integer isNavIntercept;
    private String totalNum;
    private String gainedNum;
    private Integer isFinished;
    private Integer awardNumber;
    private Integer dailyTimes;
    private String productName;
    private Integer isReceiveNutrients;
    private Integer priceRate;
    private Integer isHidden;
    private String prizeConf;
    private String popupText;
    private String client;
    private String version;
    private Integer mpSpecial;
    private String otherConf;
    private String iconUrlNew;
    
    private Integer isDmp;
}
