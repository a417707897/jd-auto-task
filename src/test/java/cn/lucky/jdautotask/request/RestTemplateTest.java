package cn.lucky.jdautotask.request;


import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.handle.notification.ScFtqqNoticeRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
    @Test
    public void scFtqqNoticeRequestTest() throws InterruptedException {
        ScFtqqNoticeRequest scFtqqNoticeRequest = new ScFtqqNoticeRequest(
                "测试99",
                "SCU101314T40cf284bbffa8b04ecabcb0de0fd39125ee1bf1788f46"
        );

        scFtqqNoticeRequest.setNotice("\\|标题1\\|标题2\\|标题3\\|标题4\\|\n" +
                "\\|-\\|-\\-\\|-\\|\n" +
                "\\|1\\|\\|\\|\\|\n" +
                "\\|2\\|\\|\\|\\|\n" +
                "\\|3\\\\|\\|\\|");
        String execute = scFtqqNoticeRequest.execute(restTemplate);
        System.out.println("execute = " + execute);


    }

}
