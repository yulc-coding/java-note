package org.ylc.note.aop.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.aop.annotation.VisitPermission;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/19
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @VisitPermission("permission-test")
    @GetMapping("/test")
    public String test() {
        System.out.println("================== step 3: doing ==================");
        return "success";
    }
}
