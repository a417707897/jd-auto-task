package cn.lucky.jdautotask.pojo.superMarket;

import lombok.Data;

@Data
public class BizResule {

    public BizResule() {
        result = false;
    }

    private Boolean result;

    private Integer bizCode;

    private String bizMsg;
}
