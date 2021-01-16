package cn.lucky.jdautotask.pojo.plantBeanIndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FriendInfo {


    /**
     * paradiseUuid : 3jet4psytowzkn7b3mgard65lm
     * plantNickName : pi***et
     * userHeadImg : https://storage.360buyimg.com/i.imageUpload/706967616c657431353732343332353933343139_big.jpg
     * nutrCount : 2
     * userLabel : 可能认识的人
     * friendType : 2
     * userType : 1
     * sort : 2
     */

    private String paradiseUuid;
    private String plantNickName;
    private String userHeadImg;
    private String nutrCount;
    private String userLabel;
    private String friendType;
    private String userType;
    private Integer sort;
}
