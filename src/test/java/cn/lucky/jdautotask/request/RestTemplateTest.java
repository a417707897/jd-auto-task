package cn.lucky.jdautotask.request;


import cn.lucky.jdautotask.pojo.plantBeanIndex.PlantBeanIndexRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().set(1,
                new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }


    /*
     * @Author zyl
     * @Description ???????????ip
     * @Date 2021/1/11 14:20
     **/
    @Test
    public void requestIp() {
        String s = restTemplate.getForObject("http://www.httpbin.org/ip", String.class);
        System.out.println("s = " + s);
    }



    @Test
    public void requestPlantBeanIndex() throws IOException {
        PlantBeanIndexRequest plantBeanIndexRequest = new PlantBeanIndexRequest();
        //??url
        List<String> bodyValue = plantBeanIndexRequest.getParam().remove("bodyValue");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(plantBeanIndexRequest.getUrl());

        //????url
        Map<String, String> stringStringMap = plantBeanIndexRequest.getParam().toSingleValueMap();
        stringStringMap.forEach((key,value)->{
            builder.queryParam(key, value);
        });
        plantBeanIndexRequest.setUrl(builder.build().toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, plantBeanIndexRequest.getHttpHeaders());


        ResponseEntity<String> responseEntity = restTemplate.postForEntity(plantBeanIndexRequest.getUrl(),
                httpEntity,
                String.class,
                bodyValue.get(0)
        );

        String body = responseEntity.getBody();
        //转化
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);

        String string = jsonNode.get("code").toString();
        System.out.println("string = " + string);

        JsonNode data = jsonNode.get("data");


        System.out.println("body = " + responseEntity.getBody());
    }
}
