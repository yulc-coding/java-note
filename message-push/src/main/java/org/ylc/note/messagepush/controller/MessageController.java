package org.ylc.note.messagepush.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.messagepush.websocket.WebSocketServer;

import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-06
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    /**
     * 获取用户列表
     */
    @GetMapping("/ws/list")
    public ResponseEntity<List<String>> list() {
        return ResponseEntity.ok(WebSocketServer.getIds());
    }

    @GetMapping("/ws/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        WebSocketServer.batchSendInfo(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    @GetMapping("/ws/pushTag/{userId}/{message}")
    public ResponseEntity<String> pushTag(@PathVariable(name = "userId") String userId, @PathVariable(name = "message") String message) {
        WebSocketServer.sendInfo(message, userId);
        return ResponseEntity.ok("WebSocket 推送消息给：" + userId);
    }

}
