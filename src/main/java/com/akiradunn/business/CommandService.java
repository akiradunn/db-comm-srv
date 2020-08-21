package com.akiradunn.business;

import com.akiradunn.common.constant.HttpProtocol;
import com.akiradunn.common.util.DbAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 置顶小组转租贴
 * @author akiradunn
 * @since 2020/6/14 2:55
 */
@Slf4j
@Component
public class CommandService {
    @Value("${business.db.homePage.url}")
    private String homePageUrl;
    @Value("${business.db.homePage.login.url}")
    private String login;
    @Value("${business.db.homePage.login.refer}")
    private String refer;
    @Value("${business.db.homePage.login.userAgent}")
    private String userAgent;
    @Value("${business.db.homePage.login.name}")
    private String loginName;
    @Value("${business.db.homePage.login.password}")
    private String loginPassword;
    @Value("${business.db.homePage.login.remember}")
    private String loginRemember;
    @Value("${business.db.group.comment}")
    private String comment;
    @Value("${business.db.group.commentUrl}")
    private String commentUrl;
    @Value("${business.db.homePage.logout}")
    private String logout;
    @Autowired
    private RestTemplate restTemplate;

    public void doProcess(){
        //获取登录所需bid cookie
        String bidCookie = acquireBidCookie();

        //执行登录
        String dbcl2Cookie = login(bidCookie);

        //获取评论所需ck cookie
        String ckCookie = acquireCkCookie(bidCookie,dbcl2Cookie);

        //评论
        addComment(dbcl2Cookie,parseCookieStr(ckCookie));

        //防屏蔽 注销
        logout(ckCookie);
    }

    /**
     * 获取bid cookie用于登录接口使用
     * @author akiradunn
     * @time 2020/6/14 15:43
     **/
    private String acquireBidCookie() {
        log.info("---开始获取bid cookie---");

        //添加请求头
        HttpHeaders headers = new HttpHeaders();
        //form表单提交
        headers.add(HttpProtocol.HOST, homePageUrl);
        //组装参数
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(homePageUrl, String.class);
        List<String> respCookie = forEntity.getHeaders().get(HttpProtocol.SETCOOKIE);
        String bidCookie = respCookie == null ? null : respCookie.get(1);

        log.info("---结束获取bid cookie, cookie 值为 {}---",bidCookie);
        return bidCookie;
    }

    /**
     * 豆瓣登录接口
     * @author akiradunn
     * @time 2020/6/14 15:57
     **/
    private String login(String bidCookie) {
        log.info("---开始登录douban---");

        //添加请求头
        HttpHeaders headers = new HttpHeaders();
        //form表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HttpProtocol.REFERER,refer);
        headers.add(HttpProtocol.USERAGENT,userAgent);
        headers.add(HttpProtocol.COOKIE,bidCookie);
        //组装参数
        MultiValueMap<String, String> formData= new LinkedMultiValueMap<>();

        formData.add("name", loginName);
        formData.add("password", loginPassword);
        formData.add("remember", loginRemember);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<String> loginResponseResponseEntity = restTemplate.postForEntity(login, request, String.class);
        log.info("登录返回值为: {}",loginResponseResponseEntity);

        List<String> respCookie = loginResponseResponseEntity.getHeaders().get(HttpProtocol.SETCOOKIE);
        String dbcl2 = respCookie == null ? null : respCookie.get(0);

        log.info("---结束登录douban,获取dbcl2 cookie值为 {}---",dbcl2);
        return dbcl2;
    }

    /**
     * 获取ck cookie用于评论接口
     * @author akiradunn
     * @time 2020/6/14 16:40
     **/
    private String acquireCkCookie(String bidCookie,String dbcl2Cookie) {
        log.info("---开始获取ck cookie---");

        String url = homePageUrl;
        //添加请求头
        HttpHeaders headers = new HttpHeaders();
        //form表单提交
        headers.add(HttpProtocol.HOST, homePageUrl);
        String cookie1 = "bid=" + parseCookieStr(bidCookie);
        String cookie2 = "dbcl2=" + parseCookieStr(dbcl2Cookie);
        String cookie = cookie1 + ";" + cookie2;
        headers.add(HttpProtocol.COOKIE, cookie);
        //组装参数
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        List<String> respCookie = forEntity.getHeaders().get(HttpProtocol.SETCOOKIE);
        String ckCookie = respCookie == null ? null : respCookie.get(0);

        log.info("---结束获取ck cookie, cookie 值为 {}---",ckCookie);
        return ckCookie;
    }

    /**
     * 评论追加置顶保持热度
     * @author akiradunn
     * @time 2020/6/14 15:58
     **/
    private void addComment(String dbcl2Cookie,String ck) {
        log.info("---开始追加评论---");

        //添加请求头
        HttpHeaders headers = new HttpHeaders();
        //form表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(HttpProtocol.HOST,homePageUrl);
        headers.add(HttpProtocol.USERAGENT,userAgent);
        headers.add(HttpProtocol.COOKIE,dbcl2Cookie);
        //组装参数
        MultiValueMap<String, String> formData= new LinkedMultiValueMap<>();

        formData.add("ck", ck);
        formData.add("rv_comment", comment);
        formData.add("submit_btn", "发送");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(commentUrl, request, String.class);

        log.info("---结束追加评论,评论返回响应数据 {}---",entity);
    }

    /**
     * 注销登录
     * @author akiradunn
     * @time 2020/6/14 16:47
     **/
    public void logout(String ckCookie) {
        log.info("---开始退出登录---");

        //添加请求头
        HttpHeaders headers = new HttpHeaders();
        //form表单提交
        headers.add(HttpProtocol.HOST, homePageUrl);
        //组装参数
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(String.format(logout,ckCookie), String.class);
        log.info("---结束退出登录,返回数据为 {}---",forEntity);
    }

    /**
     * 解析cookie字符串提取出cookie键值
     * @author akiradunn
     * @time 2020/6/14 15:56
     **/
    private String parseCookieStr(String cookieStr) {
        log.info("---开始解析cookie str: {}---",cookieStr);

        DbAssert.isTrue(cookieStr != null , "cookie为空！");
        String[] kvStr = cookieStr.split(";");
        String v = kvStr[0].split("=")[1];

        log.info("---结束解析cookie str,获得cookie: {}---",v);
        return v;
    }
}
