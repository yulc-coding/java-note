package org.ylc.note.redis.hyperloglog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.redis.bit.service.RedisService;

import java.util.Random;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-27
 */
@RestController
@RequestMapping("/redis/hll")
public class HyperController {

    private final RedisService redisService;

    public HyperController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/init")
    public String init() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                String name = Thread.currentThread().getName();
                Random r = new Random();
                int begin = r.nextInt(100) * 10000;
                int end = begin + 10000;
                for (int j = begin; j < end; j++) {
                    redisService.pfAdd("hhl:" + name, j + "");
                }
                System.out.printf("线程【%s】完成数据初始化，区间[%d, %d)\n", name, begin, end);
            },
                    i + "");
            thread.start();
        }
        return "success";
    }

    @GetMapping("/count")
    public String count() {
        long a = redisService.pfCount("hhl:0");
        long b = redisService.pfCount("hhl:1");
        long c = redisService.pfCount("hhl:2");
        long d = redisService.pfCount("hhl:3");
        long e = redisService.pfCount("hhl:4");
        System.out.printf("hhl:0 -> count: %d, rate: %f\n", a, (10000 - a) * 1.00 / 100);
        System.out.printf("hhl:1 -> count: %d, rate: %f\n", b, (10000 - b) * 1.00 / 100);
        System.out.printf("hhl:2 -> count: %d, rate: %f\n", c, (10000 - c) * 1.00 / 100);
        System.out.printf("hhl:3 -> count: %d, rate: %f\n", d, (10000 - d) * 1.00 / 100);
        System.out.printf("hhl:4 -> count: %d, rate: %f\n", e, (10000 - e) * 1.00 / 100);
        return "success";
    }

}
