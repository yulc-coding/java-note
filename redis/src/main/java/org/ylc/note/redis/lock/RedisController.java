package org.ylc.note.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final String GOODS_KEY = "goodsKey";

    private static final String LOCK_KEY = "testLock";

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/lock")
    public void lockTest() throws InterruptedException {
        // 用户唯一标识
        String lockValue = UUID.randomUUID().toString().replace("-", "");
        while (true) {
            if (redisLock.tryLock(LOCK_KEY, lockValue)) {
                logger.info("[{}]成功获取锁", lockValue);
                break;
            }
            logger.info("[{}]获取锁失败，200毫秒后重新尝试获取锁", lockValue);
            Thread.sleep(200);
        }
        // 剩余库存
        String goods = stringRedisTemplate.opsForValue().get(GOODS_KEY);
        if (goods == null) {
            logger.info("[{}]获取剩余库存失败，释放锁：{} @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
            return;
        }
        int surplus = Integer.parseInt(goods);
        if (surplus <= 0) {
            logger.info("[{}]库存不足，释放锁：{} ##########################################", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
            return;
        }

        logger.info("[{}]当前库存[{}]，操作：库存-1", lockValue, surplus);
        stringRedisTemplate.opsForValue().decrement(GOODS_KEY);
        logger.info("[{}]操作完成，开始释放锁，释放结果：{}", lockValue, redisLock.unLock(LOCK_KEY, lockValue));
    }
}
