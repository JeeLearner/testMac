package com.lyd.validator;


import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public abstract class Assert {

    public static void isBlank(String str, String message) throws Exception {
        if (StringUtils.isBlank(str)) {
            throw new Exception(message);
        }
    }

    public static void isNull(Object object, String message) throws Exception {
        if (object == null) {
            throw new Exception(message);
        }
    }
}
