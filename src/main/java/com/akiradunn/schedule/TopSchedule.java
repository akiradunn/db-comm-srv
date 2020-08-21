package com.akiradunn.schedule;
import com.akiradunn.business.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopSchedule {

    @Autowired
    private CommandService commandService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void execute() {
        log.info("定时置顶帖任务执行开始！");

        commandService.doProcess();

        log.info("定时置顶帖任务执行结束！");
    }
}
