package org.ylc.note.redis.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 位操作
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/15
 */
@RestController
@RequestMapping("/redis/bit")
public class RedisBitController {

    /**
     * 定义前缀
     */
    private static final String SIGN_PREFIX = "sign:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 全年签到信息记录
     * 设置key格式为 sing:{year}:{userId}
     */
    @GetMapping("/sign/{userId}")
    public String sign(@PathVariable String userId) {
        // 获取今天是当年的第几天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int days = calendar.get(Calendar.DAY_OF_YEAR);

        // 没有key时返回false
        Boolean signResult = stringRedisTemplate.opsForValue().setBit(SIGN_PREFIX + year + ":" + userId, days, true);
        if (signResult == null || !signResult) {
            return "签到失败";
        }
        return "签到成功";
    }

    /**
     * 查询用户今天是否已近签到了
     */
    @GetMapping("/isSign/{userId}")
    public String isSign(@PathVariable String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int days = calendar.get(Calendar.DAY_OF_YEAR);
        Boolean isSign = stringRedisTemplate.opsForValue().getBit(SIGN_PREFIX + year + ":" + userId, days);
        if (isSign == null || !isSign) {
            return String.format("用户【%s】今日尚未签到，请签到", userId);
        }
        return String.format("用户【%s】今日已签到", userId);
    }

}
