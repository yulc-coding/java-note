package org.ylc.note.security.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * redis操作
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/2
 */
@Component
public class RedisService {

    /**
     * 处理String 类型
     */
    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(毫秒)
     */
    public void expire(String key, long time) {
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                stringRedisTemplate.delete(key[0]);
            } else {
                stringRedisTemplate.delete(Arrays.asList(key));
            }
        }
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, String value, long time) {
        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }


    /**
     * 已set集合方式缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     */
    public void strSetAdd(String key, long time, String... values) {
        stringRedisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 查询set中是否存在指定值
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean memberInSet(String key, String value) {
        Boolean hasKey = stringRedisTemplate.opsForSet().isMember(key, value);
        if (hasKey == null) {
            return false;
        }
        return hasKey;
    }

}
