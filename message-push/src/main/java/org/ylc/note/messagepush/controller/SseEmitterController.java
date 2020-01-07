package org.ylc.note.messagepush.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.ylc.note.messagepush.server.SseEmitterServer;

import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * SseEmitter 推送消息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/7
 */
@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        return SseEmitterServer.connect(userId);
    }

    @GetMapping("/close/{userId}")
    public void close(@PathVariable String userId) {
        SseEmitterServer.removeUser(userId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {
        return ResponseEntity.ok(SseEmitterServer.getIds());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUserCount() {
        return ResponseEntity.ok(SseEmitterServer.getUserCount());
    }

    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    @GetMapping("/pushTag/{userId}/{message}")
    public ResponseEntity<String> pushTag(@PathVariable(name = "userId") String userId,
                                          @PathVariable(name = "message") String message) {
        SseEmitterServer.sendMessage(userId, message);
        return ResponseEntity.ok("WebSocket 推送消息给：" + userId);
    }


}
