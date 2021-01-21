package cn.lucky.jdautotask.request;

import cn.hutool.core.date.DateUtil;
import cn.lucky.jdautotask.pojo.enums.TimerGroupType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;


/**
 * 时间测试
 */

public class DateTest {
    Logger log = LoggerFactory.getLogger(getClass());
    /**
     * long转时间
     */
    @Test
    public void longToDate(){
        log.warn("1111");
        Long dateL = 1767283199000L;
        String format = DateUtil.format(new Date(dateL), "yyyy-MM-dd HH:mm:ss");
        System.out.println("format = " + format);
    }

    @Test
    public void enumTest(){
        Integer random = (int)(Math.random() * 1000000);
        System.out.println("random = " + random);
    }



}
