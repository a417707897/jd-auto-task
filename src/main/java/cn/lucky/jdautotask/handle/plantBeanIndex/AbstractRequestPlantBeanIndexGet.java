package cn.lucky.jdautotask.handle.plantBeanIndex;

import cn.lucky.jdautotask.handle.plantBeanIndex.AbstractRequestPlantBeanIndex;

/**
 * get请求种豆得豆
 */
public abstract class AbstractRequestPlantBeanIndexGet extends AbstractRequestPlantBeanIndex {

    public AbstractRequestPlantBeanIndexGet(){
        //去除一些参数
        param.remove("client");
        param.remove("area");
        param.remove("build");
        param.remove("clientVersion");
    }
}
