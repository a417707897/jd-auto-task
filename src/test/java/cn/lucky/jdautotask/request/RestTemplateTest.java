package cn.lucky.jdautotask.request;


import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.pojo.plantBeanIndex.impl.PlantBeanIndexRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class RestTemplateTest {

    private RestTemplate restTemplate;

    @Before
    public void init(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(
                new Proxy(
                        Proxy.Type.HTTP,
                        new InetSocketAddress("135.181.18.96", 38192)  //??????
                )
        );
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        restTemplate = RestTemplateConfig.getRestTemplateStringIsUtf8();
    }


    /*
     * @Author zyl
     * @Description ???????????ip
     * @Date 2021/1/11 14:20
     **/
    @Test
    public void requestIp() {
        String s = restTemplate.getForObject("http://www.httpbin.org/ip", String.class,null,null);
        System.out.println("s = " + s);
    }

}
