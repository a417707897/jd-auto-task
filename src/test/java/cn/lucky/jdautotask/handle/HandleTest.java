package cn.lucky.jdautotask.handle;

import cn.lucky.jdautotask.handle.farm.FarmDailyTasksHandle;
import cn.lucky.jdautotask.handle.nian.GetProduceFirecrackersHandle;
import cn.lucky.jdautotask.handle.nian.NianDailyTasksHandle;
import cn.lucky.jdautotask.handle.plantBeanIndex.PlantBeanIndexHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketDailyTasksHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketExchangeHandle;
import cn.lucky.jdautotask.handle.superMarket.SuperMarketTimingHandle;
import cn.lucky.jdautotask.pojo.request.JdAutoTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandleTest {

    private String cookie;

    @Before
    public void init(){
        cookie = "pt_key=AAJgN2yyADBHN8rvm_MxQIgxZiQPZOQRWM2oT5ZlwEWKDeMBynynDdrCZxMEtyRTdpC5JcIlhR0;pt_pin=18337656372_p";
    }

    @Test
    public void plantBeanIndexHandle(){
        PlantBeanIndexHandle plantBeanIndexHandle = new PlantBeanIndexHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        plantBeanIndexHandle.doExecute(jdAutoTaskRequest);
    }


    @Test
    public void getProduceFirecrackersHandle() {
        GetProduceFirecrackersHandle handle = new GetProduceFirecrackersHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void superMarketExchangeHandle() throws InterruptedException {
        SuperMarketExchangeHandle handle = new SuperMarketExchangeHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void superMarketDailyTasksHandle() throws InterruptedException {
        SuperMarketDailyTasksHandle handle = new SuperMarketDailyTasksHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void superMarketTimingHandle() throws InterruptedException {
        SuperMarketTimingHandle handle = new SuperMarketTimingHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void farmDailyTasksHandle() throws InterruptedException {
        FarmDailyTasksHandle handle = new FarmDailyTasksHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void nianDailyTasksHandle() throws InterruptedException {
        NianDailyTasksHandle handle = new NianDailyTasksHandle();
        //请求实体
        JdAutoTaskRequest jdAutoTaskRequest = JdAutoTaskRequest
                .builder()
                .cookie(cookie)
                .build();

        handle.doExecute(jdAutoTaskRequest);
    }

    @Test
    public void test() {
        //根据参数设置数据源，或者去一个容器取出来

        //代码逻辑

        //查询返回




    }
}
