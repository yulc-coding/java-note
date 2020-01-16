package org.ylc.note.redis;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * redis操作
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/16
 */
@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取指定格式的key
     *
     * @param pattern 格式
     * @return set
     */
    public Set<String> getKeys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 设置指定位的值
     *
     * @param key    键
     * @param offset 偏移量 0开始 对应bit的位置
     * @param value  true为1，false为0
     * @return boolean
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取指定位的值
     *
     * @param key    键
     * @param offset 偏移量 0开始
     * @return boolean
     */
    public Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 统计字符串被设置为1的bit数
     *
     * @param key 键
     * @return long
     */
    public Long bitCount(String key) {
        return stringRedisTemplate.execute(
                (RedisCallback<Long>) connection -> connection.bitCount(key.getBytes())
        );
    }

    /**
     * 统计字符串指定位上被设置为1的bit数
     *
     * @param key   键
     * @param start 开始位置  注意对应byte的位置,是bit位置*8
     * @param end   结束位置
     * @return long
     */
    public Long bitCount(String key, long start, long end) {
        return stringRedisTemplate.execute(
                (RedisCallback<Long>) connection -> connection.bitCount(key.getBytes(), start, end)
        );
    }

    /**
     * 不同字符串之间进行位操作
     *
     * @param op      操作类型：与、或、异或、否
     * @param destKey 最终存放结构的键
     * @param keys    要操作的键
     * @return Long
     */
    public Long bitOp(RedisStringCommands.BitOperation op, String destKey, Collection<String> keys) {
        int size = keys.size();
        byte[][] bytes = new byte[size][];

        int index = 0;
        for (String key : keys) {
            bytes[index++] = key.getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, destKey.getBytes(), bytes));
    }

    /**
     * 对符合指定格式的key值进行未操作
     *
     * @param op      操作类型：与、或、异或、否
     * @param destKey 存放结果的键
     * @param pattern key格式
     * @return Long
     */
    public Long bitOp(RedisStringCommands.BitOperation op, String destKey, String pattern) {
        Set<String> keys = getKeys(pattern);
        int size = keys.size();
        if (size == 0) {
            return 0L;
        }
        byte[][] bytes = new byte[size][];

        int index = 0;
        for (String key : keys) {
            bytes[index++] = key.getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, destKey.getBytes(), bytes));
    }
}
