package org.ylc.note.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.security.entity.User;
import org.ylc.note.security.util.RedisUtils;
import org.ylc.note.security.util.TokenUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/15
 */
@RestController
public class SecurityController {

    private final RedisUtils redisUtils;

    public SecurityController(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 模拟登陆接口
     */
    @GetMapping("/login")
    public String login(String username, String password) {
        // 模拟登陆，实际从数据库查询
        User user = new User(0L, username, password);
        String token = TokenUtil.generateToken(user);
        redisUtils.set(user.getId().toString(), token, 1000 * 60 * 30);

        // 模拟权限
        List<String> permissions = Arrays.asList(
                "/test", "/getUser"
        );
        redisUtils.strListPushAll(user.getId().toString(), permissions, 1000 * 60 * 30);
        return token;
    }


    @GetMapping("/test")

    public String test() {

        return "success";
    }
}
