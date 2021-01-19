package cn.lucky.jdautotask.pojo.enums;

/*
 * @Author zyl
 * @Description 定时器分组
 * @Date 2021/1/19 10:12
 **/
public enum  TimerGroupType {

    //京东自动任务
    JD_AUTO_TASK,

    //默认分组
    DEFAULT_GROUP;

    public String value(){
        return this.toString();
    }

}
