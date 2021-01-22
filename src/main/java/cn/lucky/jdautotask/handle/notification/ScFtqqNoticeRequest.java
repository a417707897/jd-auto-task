package cn.lucky.jdautotask.handle.notification;

import cn.lucky.jdautotask.handle.common.AbstractRequestInfo;
import cn.lucky.jdautotask.handle.common.AbstractRequestInfoWithExAction;
import cn.lucky.jdautotask.pojo.notification.ScFtqqNotice;
import cn.lucky.jdautotask.utils.AssertUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * server酱，消息通知，目前就一个就不抽取了，直接写
 */
public class ScFtqqNoticeRequest extends AbstractRequestInfoWithExAction {

    private ScFtqqNotice scFtqqNotice;


    private ScFtqqNoticeRequest(){}

    @Override
    protected void checkParam() throws JsonProcessingException {
    }

    //设置请求实体
    public ScFtqqNoticeRequest(@NonNull String title,@NonNull String scKey) {
        scFtqqNotice = new ScFtqqNotice();
        scFtqqNotice.setNotice(new ArrayList<>());
        scFtqqNotice.setScKey(scKey);
        scFtqqNotice.setTitle(title);
        httpMethod = HttpMethod.POST;
        url = "https://sc.ftqq.com/" + scKey + ".send";
        httpHeaders.set("Content-type","application/x-www-form-urlencoded");
    }

    public ScFtqqNoticeRequest setNotice(@NonNull String notice) {
        scFtqqNotice.getNotice().add(notice);
        return this;
    }


    @Override
    public String execute(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "请求实体不能为空");
        List<String> notice = scFtqqNotice.getNotice();
        Assert.notEmpty(notice, "发送的消息列表为空");
        param.set("text", scFtqqNotice.getTitle());
        param.set("desp", scFtqqNotice.getNoticeStr());
        super.paramLinkSet();
        ResponseEntity<String> exchange = restTemplate.exchange(this.url,
                httpMethod,
                super.getHttpEntity(),
                String.class,
                super.getPlaceholderValue());

        return exchange.getBody();
    }

}
