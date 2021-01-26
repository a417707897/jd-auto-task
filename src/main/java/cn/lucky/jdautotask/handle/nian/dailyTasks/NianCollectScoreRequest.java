package cn.lucky.jdautotask.handle.nian.dailyTasks;


import cn.hutool.core.util.StrUtil;
import cn.lucky.jdautotask.handle.nian.AbstractNianShouRequestPost;
import cn.lucky.jdautotask.utils.AssertUtil;
import cn.lucky.jdautotask.utils.JsonFormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * 做任务
 */
@Log4j2
public class NianCollectScoreRequest extends AbstractNianShouRequestPost {


    public NianCollectScoreRequest() {
        setFunctionId("nian_collectScore");
    }

    public void setBody(String secretp, String taskId,
                        String itemId, String actionType,
                        String inviteId, String shopSign) throws JsonProcessingException {
        AssertUtil.strNotNull(secretp, "secretp不能为空");
        AssertUtil.strNotNull(taskId, "taskId不能为空");
        AssertUtil.strNotNull(itemId, "itemId不能为空");

        Map<String, Object> extraData = new HashMap<>();
        extraData.put("jj", 6);
        extraData.put("buttonid", "jmdd-react-smash_0");
        extraData.put("sceneid", "homePageh5");
        extraData.put("appid", "50073");

        Map<String, Object> businessData = new HashMap<>();
        businessData.put("taskId", taskId);
        businessData.put("rnd", (int) (Math.random() * 1000000));
        businessData.put("inviteId", "-1");
        businessData.put("stealId", "-1");
        businessData.put("itemId", itemId);
        if (StrUtil.isNotBlank(actionType)) {
            businessData.put("actionType", actionType);
        }

        if (StrUtil.isNotBlank(inviteId)) {
            businessData.put("inviteId", inviteId);
        }

        if (StrUtil.isNotBlank(shopSign)) {
            businessData.put("shopSign", shopSign);
        }



        Map<String, String> temp = new HashMap<>();
        temp.put("secretp", secretp);
        temp.put("extraData", JsonFormatUtil.getObjectMapper().writeValueAsString(extraData));
        temp.put("businessData", JsonFormatUtil.getObjectMapper().writeValueAsString(businessData));

        Map<String, Object> bodyTemp = new HashMap<>();
        bodyTemp.put("ss", temp);
        bodyTemp.put("taskId", taskId);
        bodyTemp.put("itemId", itemId);
        if (StrUtil.isNotBlank(actionType)) {
            bodyTemp.put("actionType", actionType);
        }

        if (StrUtil.isNotBlank(inviteId)) {
            bodyTemp.put("inviteId", inviteId);
        }

        if (StrUtil.isNotBlank(shopSign)) {
            bodyTemp.put("shopSign", shopSign);
        }

        setBodyByStr(JsonFormatUtil.jsonFormatObjectToStr(bodyTemp));
    }


    @Override
    protected void checkParam() throws JsonProcessingException {

    }
}
