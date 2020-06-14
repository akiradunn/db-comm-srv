package com.akiradunn.dbcommsrv.common.util;

/**
 * @description:断言
 * @projectName:db-comm-srv
 * @author:duanzengliang
 * @time:2020/6/14 15:49
 */
public class AssertUtil {
    public static void isTrue(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }
}
