package com.chinatop.contains;


import com.chinatop.contains.model.AyMood;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.model.AyUserAttachmentRel;
import com.chinatop.contains.producerAndcomsumer.AyMoodProducer;
import com.chinatop.contains.service.AyMoodService;
import com.chinatop.contains.service.AyUserAttachmentRelService;
import com.chinatop.contains.service.AyUserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    Logger logger = LogManager.getLogger(this.getClass());
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private AyUserService ayUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AyMoodService ayMoodService;
    @Resource
    private AyMoodProducer ayMoodProducer;
    @Resource
    private AyUserAttachmentRelService ayUserAttachmentRelService;

    @Test
    public void contextLoads() {
        String sql = "select * from ay_user";
        List<AyUser> userList = jdbcTemplate.query(sql, new RowMapper<AyUser>() {

            @Override
            public AyUser mapRow(ResultSet resultSet, int i) throws SQLException {
                AyUser ayUser = new AyUser();
                ayUser.setId(resultSet.getString("id"));
                ayUser.setName(resultSet.getString("name"));
                ayUser.setPassword(resultSet.getString("password"));
                return ayUser;
            }
        });
        System.out.println("查询成功：");
        for (AyUser ayuser : userList
        ) {
            System.out.println("[id] : " + ayuser.getId() + ",[name] : " + ayuser.getName() + ",[password] : " + ayuser.getPassword());
        }
    }

    @Test
    public void testTransaction() {
        AyUser ayUser = new AyUser();
        ayUser.setId("3");
        ayUser.setName("阿华");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);
    }

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "ay");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
        redisTemplate.delete("name");
        redisTemplate.opsForValue().set("name", "al");
        name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void testFindById() {
        Long redisAyuserSize = 0L;
        AyUser ayUser = ayUserService.findById("1");
        redisAyuserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisAyuserSize);
        System.out.println("--->>>id：" + ayUser.getId() + " name：" + ayUser.getName());
        AyUser ayUser1 = ayUserService.findById("2");
        redisAyuserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisAyuserSize);
        System.out.println("--->>>id：" + ayUser1.getId() + " name：" + ayUser1.getName());
        AyUser ayUser3 = ayUserService.findById("4");
        redisAyuserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisAyuserSize);
        System.out.println("--->>>id：" + ayUser3.getId() + " name：" + ayUser3.getName());
    }

    @Test
    public void testLog4j2() {
        ayUserService.delete("4");
        logger.info("delete success!!!");
    }

    @Test
    public void testMybatis() {
        AyUser ayUser = ayUserService.findByNameAndPassword("阿毅", "123456");
        logger.info("AyUserName：" + ayUser.getName() + " AyUserPassword：" + ayUser.getPassword());
    }

    @Test
    public void testAyMood() {
        AyMood ayMood = new AyMood();
        ayMood.setId("1");
        ayMood.setUserId("1");
        ayMood.setPraiseNum(0);
        ayMood.setContent("这是我的第一条微信说说！！！");
        ayMood.setPublishTime(new Date());
        AyMood mood = ayMoodService.save(ayMood);
        System.out.println("id：" + mood.getId() + " user_id：" + mood.getUserId() + " content：" + mood.getContent() + " 点赞数：" + mood.getPraiseNum() + " 发布日期：" + mood.getPublishTime());
    }

    @Test
    public void testActiveMQ() {
        Destination destination = new ActiveMQQueue("ay.queue");
        ayMoodProducer.sendMessage(destination, "hello mq!!!");
    }

    @Test
    public void testActiveMQAsyncSave() {
        AyMood ayMood = new AyMood();
        ayMood.setId("2");
        ayMood.setUserId("2");
        ayMood.setPraiseNum(0);
        ayMood.setContent("这是我的第一条微信说说！！！");
        ayMood.setPublishTime(new Date());
        String msg = ayMoodService.asyncSave(ayMood);
        System.out.println("异步发表说说：" + msg);
    }

    @Test
    public void testAsync() {
        long startTime = System.currentTimeMillis();
        System.out.println("第一次查询所有用户！");
        List<AyUser> ayUserList = ayUserService.findAll();
        System.out.println("第二次查询所有用户！");
        ayUserList = ayUserService.findAll();
        System.out.println("第三次查询所有用户！");
        ayUserList = ayUserService.findAll();
        long endTime = System.currentTimeMillis();
        System.out.println("总共消耗" + (endTime - startTime) + "毫秒");
    }

    @Test
    public void testAsync2() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("第一次查询所有用户！");
        Future<List<AyUser>> ayUserList = ayUserService.findAsyncAll();
        System.out.println("第二次查询所有用户！");
        Future<List<AyUser>> ayUserList2 = ayUserService.findAsyncAll();
        System.out.println("第三次查询所有用户！");
        Future<List<AyUser>> ayUserList3 = ayUserService.findAsyncAll();
        while (true) {
            if (ayUserList.isDone() && ayUserList2.isDone() && ayUserList3.isDone()) {
                break;
            } else {
                Thread.sleep(10);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("查询结束，总共耗时：" + (endTime - startTime) + "毫秒");
    }

    @Test
    public void testMongoDB() {
        AyUserAttachmentRel ayUserAttachmentRel = new AyUserAttachmentRel();
        ayUserAttachmentRel.setId("1");
        ayUserAttachmentRel.setUserId("1");
        ayUserAttachmentRel.setFileName("个人简历.doc");
        ayUserAttachmentRelService.save(ayUserAttachmentRel);
        System.out.println("保存成功！");

    }

    @Test
    public void testFindByUserName() {
        AyUser ayUser = ayUserService.findByUserName("阿毅");
        System.out.println("名称：" + ayUser.getName() + " 密码：" + ayUser.getPassword());
    }
}
