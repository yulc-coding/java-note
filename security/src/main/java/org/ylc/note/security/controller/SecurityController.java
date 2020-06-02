package org.ylc.note.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.security.common.HttpResult;
import org.ylc.note.security.entity.User;
import org.ylc.note.security.mapper.UserMapper;
import org.ylc.note.security.service.RedisService;

import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-05-18
 */
@RestController
public class SecurityController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @GetMapping("/list")
    public HttpResult<List<User>> userList() {

        return HttpResult.success(userMapper.list());
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/hasPermission")
    public HttpResult<Boolean> hasPermission() {
        return HttpResult.success(redisService.memberInSet("1312321", "test"));
    }
}
