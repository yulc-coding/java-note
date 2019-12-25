package org.ylc.note.commonspringboottest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.demo.BirdService;
import org.ylc.note.demo.FishService;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 测试demo
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-25
 */

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final FishService fishService;

    private final BirdService birdService;

    public AnimalController(FishService fishService, BirdService birdService) {
        this.fishService = fishService;
        this.birdService = birdService;
    }

    @GetMapping("/fish")
    public String fish() {
        fishService.doing();
        return "fish";
    }

    @GetMapping("/bird")
    public String bird() {
        birdService.doing();
        return "bird";
    }

}
