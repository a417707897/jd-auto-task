package cn.lucky.jdautotask.handle;

import cn.lucky.jdautotask.handle.plantBeanIndex.PlantBeanIndexHandle;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import org.junit.Test;

public class PlantBeanIndexHandleTest {

    @Test
    public void doExecuteTest(){
        PlantBeanIndexHandle plantBeanIndexHandle = new PlantBeanIndexHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie("pt_key=AAJf5w7aADAy8wXy7TrGhb6ico1jELCHQJTC7npw114j2KN2VB3EblC4297Hk5PKX433RhGRHDc;pt_pin=18337656372_p")
                .build();

        plantBeanIndexHandle.doExecute(jdAutoTaskRequest);


    }
}
