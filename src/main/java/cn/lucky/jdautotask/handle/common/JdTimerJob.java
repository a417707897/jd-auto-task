package cn.lucky.jdautotask.handle.common;

import cn.lucky.jdautotask.config.user.JdUserInfo;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 京东定时器的抽象
 */
public abstract class JdTimerJob implements Job {

    @Autowired
    protected JdUserInfo jdUserInfo;

    protected AbstractJdAutoTaskHandle handle;



}
