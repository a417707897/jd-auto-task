package cn.lucky.jdautotask.request;


import cn.lucky.jdautotask.config.request.RestTemplateConfig;
import cn.lucky.jdautotask.pojo.plantBeanIndex.impl.PlantBeanIndexRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

/*
 * @Author zyl
 * @Description 种豆得豆
 * @Date 2021/1/14 17:22
 **/
public class PlantBeanIndexTest {

    private RestTemplate restTemplate;

    @Before
    public void init(){
        restTemplate = RestTemplateConfig.getRestTemplateStringIsUtf8();
    }

    /*
     * @Author zyl
     * @Description 获取种豆得豆任务详情
     * @Date 2021/1/14 17:03
     * @Param []
     * @return void
     **/
    @Test
    public void requestPlantBeanIndex() throws IOException {
        PlantBeanIndexRequest plantBeanIndexRequest = new PlantBeanIndexRequest();
        plantBeanIndexRequest.setCookie("pt_key=AAJf98fdADAkTZxZqh2W5jOskf7cA0YaKQDNWcqyX5sTPK_YeQqxgdGKHZjssizJDjam8k6G-ME;pt_pin=jd_SBznbkgNHMvQ");

        String body = plantBeanIndexRequest.execute(restTemplate);
        //转化
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);

        String string = jsonNode.get("code").toString();
        System.out.println("string = " + string);
        JsonNode data = jsonNode.get("data");
    }

}
