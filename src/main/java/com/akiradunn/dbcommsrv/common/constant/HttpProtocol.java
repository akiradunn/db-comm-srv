package com.akiradunn.dbcommsrv.common.constant;

/**
 * @description:
 * @projectName:db-comm-srv
 * @author:duanzengliang
 * @time:2020/6/14 17:35
 */
public interface HttpProtocol {
    public static final String HOST = "Host";

    /** 响应投携带cookie **/
    public static final String SETCOOKIE = "Set-Cookie";
    public static final String REFERER = "Referer";
    public static final String USERAGENT = "User-Agent";

    /** 请求头携带cookie **/
    public static final String COOKIE = "cookie";
}
