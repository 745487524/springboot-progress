package com.chinatop.contains.service.impl;

import com.chinatop.contains.model.AyMood;
import com.chinatop.contains.producerAndcomsumer.AyMoodProducer;
import com.chinatop.contains.repository.AyMoodRepository;
import com.chinatop.contains.service.AyMoodService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.List;

@Service
public class AyMoodServiceImpl implements AyMoodService {
    private static String ALL_MOOD = "ALL__USER_MOOD";
    private static Destination destination = new ActiveMQQueue("ay.queue.asyn.save");
    @Resource
    private AyMoodRepository ayMoodRepository;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyMoodProducer ayMoodProducer;

    @Override
    public AyMood save(AyMood ayMood) {
        AyMood ayMood1 = ayMoodRepository.save(ayMood);
        List<AyMood> ayMoodList = redisTemplate.opsForList().range(ALL_MOOD, 0, -1);
        if (ayMoodList == null || ayMoodList.size() <= 0) {
            ayMoodList = ayMoodRepository.findAll();
        }
        return ayMood;
    }

    @Override
    public String asyncSave(AyMood ayMood) {
        ayMoodProducer.sendMessage(destination, ayMood);
        return "success";
    }
}
