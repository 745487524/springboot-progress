package com.chinatop.contains.listener;

import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class AyUserListener implements ServletContextListener {

    private static final String ALL_USER = "ALL_USER_LIST";
    Logger logger = LogManager.getLogger(this.getClass());
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyUserService ayUserService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("ServletContext 上下文初始化");
        logger.info("ServletContext上下文初始化");
        List<AyUser> ayUserList = ayUserService.findAll();
        redisTemplate.delete(ALL_USER);
        redisTemplate.opsForList().leftPushAll(ALL_USER, ayUserList);
        List<AyUser> queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
//        System.out.println("缓存中目前的用户数有："+queryUserList.size()+"人");
        logger.info("缓存中目前的用户数有：" + queryUserList.size() + "人");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("ServletContext 上下文销毁");
        logger.info("ServletContext上下文销毁");
    }
}
