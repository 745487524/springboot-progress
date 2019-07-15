package com.chinatop.contains.quartz;

import com.chinatop.contains.mail.SendJunkMailService;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {
    //    日志d对象
    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    @Autowired
    private SendJunkMailService sendJunkMailService;
    @Resource
    private AyUserService ayUserService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void reportCurrentByCron() {
        List<AyUser> ayUserList = ayUserService.findAll();
        if (ayUserList == null || ayUserList.size() <= 0) {
            return;
        }
        sendJunkMailService.sendJunkMail(ayUserList);
        logger.info("定时器2运行了！！！" + System.currentTimeMillis());
    }
}
