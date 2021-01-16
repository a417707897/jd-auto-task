package cn.lucky.jdautotask.handle.common;

import java.util.Map;

/*
 * @Author zyl
 * @Description 自动任务的具体操作逻辑
 * @Date 2021/1/15 11:04
 **/
public interface AutoTaskLogic<T> {

    /*
     * @Author zyl
     * @Description 执行逻辑
     * @Date 2021/1/15 11:08
     * @Param []
     * @return void
     **/
    void doExecute(T t) throws InterruptedException;

    /*
     * @Author zyl
     * @Description 把相应的Bean设置进去
     * @Date 2021/1/15 11:23
     * @Param [beanMap]
     * @return void
     **/
    void setBeans(Map<String, Object> beanMap);
}
