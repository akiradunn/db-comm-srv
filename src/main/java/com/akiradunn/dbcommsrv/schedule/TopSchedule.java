package com.akiradunn.dbcommsrv.schedule;
import com.akiradunn.dbcommsrv.business.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopSchedule {

    @Autowired
    private CommandService commandService;

    @Scheduled(cron = "0 0/30 0-23 * * ?")
    public void execute() {
        log.info("定时置顶帖任务执行开始！");

        commandService.doProcess();

        log.info("定时置顶帖任务执行结束！");
    }
}
