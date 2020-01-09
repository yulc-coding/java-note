package org.ylc.note.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * Redis分布式锁
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/9
 */
public class RedisLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁
     */
    public Boolean lock() {
        /*
         * 如果这个key存在则返回 false，
         * 不存在的，添加可以，设置过期时间，返回true
         * 调用 set k v ex 10 nx 命令 (ex表示单位为秒，px表示单位是毫秒)
         */
        return redisTemplate.opsForValue().setIfAbsent("lockKey", "123", 10, TimeUnit.SECONDS);
    }


    /**
     * 解锁
     */
    public Boolean un_lock() {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/DelKey.lua")));
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList("key"), "");
        System.out.println(result);

        return true;
    }


}
