package org.ylc.note.redis.lock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.redis.lock.RedisLock;

import java.util.Random;
import java.util.UUID;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/10
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PRODUCT_KEY = "productKey";

    private static final String LOCK_KEY = "redisLock";

    private final RedisLock redisLock;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisController(RedisLock redisLock, StringRedisTemplate stringRedisTemplate) {
        this.redisLock = redisLock;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/lock")
    public void lockTest() throws InterruptedException {
        // 用户唯一标识
        String lockValue = UUID.randomUUID().toString().replace("-", "");
        Random random = new Random();
        int sleepTime;
        while (true) {
            if (redisLock.tryLock(LOCK_KEY, lockValue)) {
                logger.info("[{}]成功获取锁", lockValue);
                break;
            }
            sleepTime = random.nextInt(1000);
            Thread.sleep(sleepTime);
            logger.info("[{}]获取锁失败，{}毫秒后重新尝试获取锁", lockValue, sleepTime);
        }
        // 剩余库存
        String products = stringRedisTemplate.opsForValue().get(PRODUCT_KEY);
        if (products == null) {
            logger.info("[{}]获取剩余库存失败，释放锁：{} @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
            return;
        }
        int surplus = Integer.parseInt(products);
        if (surplus <= 0) {
            logger.info("[{}]库存不足，释放锁：{} ##########################################", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
            return;
        }

        logger.info("[{}]当前库存[{}]，操作：库存-1", lockValue, surplus);
        stringRedisTemplate.opsForValue().decrement(PRODUCT_KEY);
        logger.info("[{}]操作完成，开始释放锁，释放结果：{}", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
    }
}
