package cn.lucky.jdautotask.pojo.notification;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ScFtqqNotice {

    private String title;

    private List<String> notice;

    private String scKey;

    public String getNoticeStr() {

        String noticeStr = "";
        for (int i = 0; i < notice.size(); i++) {
            noticeStr += notice.get(0)+"\n";
        }

        return noticeStr;
    }
}
