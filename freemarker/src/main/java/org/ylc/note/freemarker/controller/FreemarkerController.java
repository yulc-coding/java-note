package org.ylc.note.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ylc.note.freemarker.util.FreemarkerUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * FileController
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-03-19
 */
@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

    /**
     * freemarker:(跳转到 person.ftl).
     */
    @RequestMapping("/ftl")
    public String freemarker(Map<String, Object> map) {
        map.put("name", "Joe");
        map.put("sex", 1);    //sex:性别，1：男；0：女；

        // 模拟数据
        List<Map<String, Object>> friends = new ArrayList<>();
        Map<String, Object> friend = new HashMap<>();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = new HashMap<>();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
        return "person";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("p1", "甲方");
        dataMap.put("p2", "乙方");
        dataMap.put("data", "2018-11-25");

        FreemarkerUtil.exportFile(dataMap, "temp.ftl", "/word", "outFile.doc", response);
    }
}
