package com.chinatop.contains.service.impl;

import com.chinatop.contains.dao.AyUserDao;
import com.chinatop.contains.error.BusinessException;
import com.chinatop.contains.model.AyUser;
import com.chinatop.contains.repository.AyUserRespository;
import com.chinatop.contains.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@Transactional
@Service
public class AyUserServiceImpl implements AyUserService {

    private static final String ALL_USER = "ALL_USER_LIST";
    Logger logger = LogManager.getLogger(this.getClass());
    @Resource(name = "ayUserRespository")
    private AyUserRespository ayUserRespository;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyUserDao ayUserDao;

    @Override
    public AyUser findById(String id) {
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (ayUserList != null && ayUserList.size() > 0) {
            for (AyUser ayUser : ayUserList) {
                if (ayUser.getId().equals(id)) {
                    return ayUser;
                }
            }
        }
        AyUser ayUser = ayUserRespository.findById(id).get();
        if (ayUser != null) {
            redisTemplate.opsForList().leftPush(ALL_USER, ayUser);
        }
        return ayUser;
    }

    @Override
    public List<AyUser> findAll() {
        try {
            System.out.println("开始做任务！！！");
            long start = System.currentTimeMillis();
            List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
            if (ayUserList == null || ayUserList.size() == 0) {
                ayUserList = ayUserRespository.findAll();
                redisTemplate.opsForList().leftPush(ALL_USER, ayUserList);
            }
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时：" + (end - start) + "ms");
            return ayUserList;
        } catch (Exception e) {
            logger.info("method[findAll] error", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    @Transactional
    public AyUser save(AyUser ayUser) {
        AyUser saveUser = ayUserRespository.save(ayUser);
        redisTemplate.opsForList().leftPush(ALL_USER, ayUser);
        return saveUser;
    }

    @Override
    public void delete(String id) {
        ayUserRespository.deleteById(id);
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (ayUserList != null && ayUserList.size() > 0) {
            for (AyUser ayUser : ayUserList) {
                if (ayUser.getId().equals(id)) {
                    redisTemplate.delete(ayUser);
                }
            }
        } else {
            redisTemplate.opsForList().leftPush(ALL_USER, ayUserRespository.findAll());
        }
        logger.info("userId：" + id + "用户被删除");
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRespository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String name) {
        return ayUserRespository.findByName(name);
    }

    @Override
    public AyUser findByUserName(String name) {
        return ayUserDao.findByUserName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRespository.findByNameLike(name);
    }

    @Override
    public List<AyUser> fingByIdIn(List<String> ids) {
        return ayUserRespository.findByIdIn(ids);
    }

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        AyUser ayUserReturn = null;
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (ayUserList == null || ayUserList.size() <= 0) {
            ayUserList = ayUserRespository.findAll();
        }
        for (AyUser ayUser : ayUserList) {
            if (ayUser.getName().equals(name) && ayUser.getPassword().equals(password)) {
                ayUserReturn = ayUser;
            }
        }
        return ayUserReturn;
    }

    @Override
    @Async
    public Future<List<AyUser>> findAsyncAll() {
        try {
            System.out.println("开始做任务！！！");
            long startTime = System.currentTimeMillis();
            List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
            if (ayUserList == null || ayUserList.size() <= 0) {
                ayUserList = ayUserRespository.findAll();
                ;
            }
            long endTIme = System.currentTimeMillis();
            System.out.println("任务结束，总共耗时：" + (endTIme - startTime) + "毫秒");
            return new AsyncResult<List<AyUser>>(ayUserList);
        } catch (Exception e) {
            logger.info("method[findAsyncAll] error", e);
            return new AsyncResult<List<AyUser>>(null);
        }
    }

    @Override
    @Retryable(value = {BusinessException.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000, multiplier = 2))
    public AyUser findByNameAndPasswordRetry(String name, String password) {
        System.out.println("[findByNameAndPasswordRetry]方法失败重试了！");
        throw new BusinessException();
    }
}
