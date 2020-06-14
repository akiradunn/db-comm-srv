package com.akiradunn.dbcommsrv.common.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:豆瓣首页消息转换器
 * @projectName:db-comm-srv
 * @author:duanzengliang
 * @time:2020/6/14 15:10
 */
public class DBMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public DBMappingJackson2HttpMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }
}
