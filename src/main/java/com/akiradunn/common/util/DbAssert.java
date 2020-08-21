package com.akiradunn.common.util;

/**
 * 断言
 * @author akiradunn
 * @since 2020/6/14 15:49
 */
public class DbAssert {
    public static void isTrue(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }
}
