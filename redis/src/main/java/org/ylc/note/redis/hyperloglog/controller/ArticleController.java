package org.ylc.note.redis.hyperloglog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.redis.hyperloglog.annotation.Article;
import org.ylc.note.redis.service.RedisService;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 文章统计相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-30
 */
@RestController
@RequestMapping("/redis/article")
public class ArticleController {

    private final RedisService redisService;

    public ArticleController(RedisService redisService) {
        this.redisService = redisService;
    }

    @Article("it")
    @GetMapping("/it")
    public String it(String userId) {
        String pv = redisService.get("PV:it");
        long uv = redisService.pfCount("UV:it");
        return String.format("当前用户：【%s】，当前it类热度：【%s】，访问用户数：【%d】", userId, pv, uv);
    }

    @Article("news")
    @GetMapping("/news")
    public String news(String userId) {
        String pv = redisService.get("PV:news");
        long uv = redisService.pfCount("UV:news");
        return String.format("当前用户：【%s】，当前news类热度：【%s】，访问用户数：【%d】", userId, pv, uv);
    }

    @GetMapping("/statistics")
    public Object statistics() {
        String pvIt = redisService.get("PV:it");
        long uvIt = redisService.pfCount("UV:it");

        String pvNews = redisService.get("PV:news");
        long uvNews = redisService.pfCount("UV:news");

        redisService.pfMerge("UV:merge", "UV:it", "UV:news");
        long uvMerge = redisService.pfCount("UV:merge");

        Map<String, String> result = new HashMap<>();
        result.put("it", String.format("it类热度：【%s】，访问用户数：【%d】；", pvIt, uvIt));
        result.put("news", String.format("news类热度：【%s】，访问用户数：【%d】", pvNews, uvNews));
        result.put("merge", String.format("合并后访问用户数：【%d】", uvMerge));
        return result;
    }
}
