package cn.lucky.jdautotask.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 断言工具
 */
public class AssertUtil {


    /**
     * 字符串不能为空
     * @param str
     * @param message
     */
    public static void strNotNull(String str,String message){
        if (StrUtil.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }

}
