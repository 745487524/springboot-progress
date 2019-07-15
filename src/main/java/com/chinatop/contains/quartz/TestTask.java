package com.chinatop.contains.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestTask {
    //    日志d对象
    private static final Logger logger = LogManager.getLogger(TestTask.class);

    public void run() {
        logger.info("定时器1运行了！！！");
    }
}
