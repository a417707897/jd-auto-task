package cn.lucky.jdautotask.request;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RestTemplateTest {


    @Test
    public void requestOne(){
        RestTemplate restTemplate = new RestTemplate();
    }


}
