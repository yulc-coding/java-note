package org.ylc.note.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
