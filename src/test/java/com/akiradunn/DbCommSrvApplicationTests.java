package com.akiradunn;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//单元测试的优势在于可以局部方法测试，如果依赖整个项目启动测试，那就只能全局测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbCommSrvApplicationTests {

    @Test
    public void contextLoads(){

    }

    @Test
    public void testQuery(){

    }
}

