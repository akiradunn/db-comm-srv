package com.akiradunn.dbcommsrv.business;

import com.akiradunn.dbcommsrv.DbCommSrvApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CommandServiceTest extends DbCommSrvApplicationTests {
    @Autowired
    private CommandService commandService;

    @Test
    public void doProcess() {
        commandService.doProcess();
    }
}