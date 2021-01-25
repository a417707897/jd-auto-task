package cn.lucky.jdautotask.pojo.farm;


import lombok.Data;

@Data
public class userBrowseTaskAds {


    /**
     * advertId : 1501445930
     * mainTitle : 每日签到抢888京豆
     * subTitle : 浏览6秒，奖励5g水滴，每天1次
     * wechatPic : https://m.360buyimg.com/babel/jfs/t1/140702/18/538/16613/5ee38cf1Efc4e420f/cc14c784cefccc3d.png
     * link : https://prodev.m.jd.com/jdjr/active/2CqUEpmWbdtzYmby7JiSEvvNHSBe/index.html?showhead=no&qidian_sid=27M9U&babelChannel=nhjsc_ddnclingshuidi
     * picurl : https://m.360buyimg.com/babel/jfs/t1/160239/22/2408/16613/5ff86bfdE88cdbcc3/e0d75f774bb1a61f.png
     * wechatLink : https://pro.m.jd.com/mall/active/4DhgEj2cD2cowRsi7syw3AaqvKBJ/index.html
     * wechatMain : 浏览特价商品
     * wechatSub : 浏览6秒，奖励5g水滴，每天1次
     * type : h5
     * reward : 5
     * limit : 1
     * time : 6
     * hadGotTimes : 0
     * hadFinishedTimes : 0
     */

    private String advertId;
    private String mainTitle;
    private String subTitle;
    private String wechatPic;
    private String link;
    private String picurl;
    private String wechatLink;
    private String wechatMain;
    private String wechatSub;
    private String type;
    private int reward;
    private int limit;
    private int time;
    private int hadGotTimes;
    private int hadFinishedTimes;
}
