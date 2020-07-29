package org.ylc.note.shardingsphere.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.shardingsphere.entity.User;
import org.ylc.note.shardingsphere.mapper.UserMapper;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author Yulc
 * @date 2020/7/29
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<User> searchUser(@RequestParam(value = "username") String username) {
        User user = userMapper.searchUser(username);
        return ResponseEntity.ok(user);
    }
}
