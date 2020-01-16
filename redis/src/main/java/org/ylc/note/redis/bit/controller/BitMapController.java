package org.ylc.note.redis.bit.controller;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.redis.RedisService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
public class BitMapController {

    /**
     * 定义签到前缀
     * key格式为 sing:{yyyyMMdd}
     */
    private static final String SIGN_PREFIX = "sign:";

    private static final String SIGN_ALL_WEEK_KEY = "signAllWeek";

    private static final String SIGN_ALL_MONTH_KEY = "signAllMonth";

    private static final String SIGN_IN_WEEK_KEY = "signInWeek";

    private final RedisService redisService;

    public BitMapController(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 按日期记录签到信息
     * 用户ID作为位图的偏移量
     */
    @GetMapping("/sign/{userId}")
    public String sign(@PathVariable Long userId) {
        redisService.setBit(SIGN_PREFIX + getCurDate(), userId, true);
        return "签到成功";
    }

    /**
     * 查询用户今天是否已经签到了
     */
    @GetMapping("/isSign/{userId}")
    public String isSign(@PathVariable Long userId) {
        Boolean isSign = redisService.getBit(SIGN_PREFIX + getCurDate(), userId);
        if (isSign) {
            return String.format("用户【%d】今日已签到", userId);
        }
        return String.format("用户【%d】今日尚未签到，请签到", userId);
    }

    /**
     * 统计今天所有的签到数量
     * 即统计
     */
    @GetMapping("/todayCount")
    public String todayCount() {
        return String.format("今日已签到人数: %d", redisService.bitCount(SIGN_PREFIX + getCurDate()));
    }

    /**
     * 获取用户全年的签到数
     */
    @GetMapping("/userYearSign/{userId}")
    public String userYearSign(@PathVariable Long userId) {
        int year = LocalDate.now().getYear();
        // 获取所有的key
        Set<String> keys = redisService.getKeys(SIGN_PREFIX + year + "*");
        // 使用BitSet 去存储用户每天的签到信息
        BitSet users = new BitSet();
        AtomicInteger index = new AtomicInteger();
        keys.forEach(key -> users.set(index.getAndIncrement(), redisService.getBit(key, userId)));

        int signCount = users.cardinality();
        return String.format("本年已累计签到： %d 次", signCount);
    }

    /**
     * 近7天连续签到的用户数量
     */
    @GetMapping("/signAllWeek")
    public String signAllWeek() {
        List<String> weekDays = getWeekKeys();
        redisService.bitOp(RedisStringCommands.BitOperation.AND, SIGN_ALL_WEEK_KEY, weekDays);
        return String.format("近7天连续签到用户数：%d", redisService.bitCount(SIGN_ALL_WEEK_KEY));
    }

    /**
     * 本月全部签到过的用户数量
     */
    @GetMapping("/signAllMonth")
    public String signAllMonth() {
        redisService.bitOp(
                RedisStringCommands.BitOperation.AND,
                SIGN_ALL_MONTH_KEY,
                SIGN_PREFIX + LocalDate.now().getYear()
        );
        return String.format("月全部签到过的用户数：%d", redisService.bitCount(SIGN_ALL_MONTH_KEY));
    }

    /**
     * 近7天有过签到的用户数量，只签到1次也算
     */
    @GetMapping("/signInWeek")
    public String signInWeek() {
        List<String> weekDays = getWeekKeys();
        redisService.bitOp(RedisStringCommands.BitOperation.OR, SIGN_IN_WEEK_KEY, weekDays);
        return String.format("近7天有过签到的用户数：%d", redisService.bitCount(SIGN_IN_WEEK_KEY));
    }

    /**
     * 获取当天的日期
     *
     * @return yyyyMMdd
     */
    private String getCurDate() {
        return LocalDate.now().toString().replace("-", "");
    }

    /**
     * 获取近一周的日期对应的key
     */
    private List<String> getWeekKeys() {
        List<String> dateList = new ArrayList<>();
        LocalDate curDate = LocalDate.now();
        dateList.add(SIGN_PREFIX + curDate.toString().replace("-", ""));
        for (int i = 1; i < 7; i++) {
            dateList.add(SIGN_PREFIX + curDate.plusDays(-i).toString().replace("-", ""));
        }
        return dateList;
    }
}