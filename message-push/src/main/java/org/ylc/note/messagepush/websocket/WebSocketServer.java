package org.ylc.note.messagepush.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * WebSocket 服务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-06
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 使用map对象，便于根据userId来获取对应的WebSocket，或者放redis里面
     */
    private static ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 对应的用户ID
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            websocketMap.put(userId, this);
            logger.info("websocket 新连接：{}", userId);
        } catch (Exception e) {
            logger.error("websocket 新建连接 IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 删除
        websocketMap.remove(this.userId);
        logger.info("close websocket : {}", this.userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("来自客户端{}的消息:{}", this.userId, message);
    }

    @OnError
    public void onError(Throwable error) {
        logger.info("websocket 发生错误,移除当前websocket:{},err:{}", this.userId, error.getMessage());
        websocketMap.remove(this.userId);
    }

    /**
     * 发送消息 (异步发送)
     *
     * @param message 消息主题
     */
    private void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 向指定用户发送信息
     *
     * @param wsInfo 信息
     * @param uId    用户id
     */
    public static void sendInfo(String wsInfo, String uId) {
        if (websocketMap.containsKey(uId)) {
            websocketMap.get(uId).sendMessage(wsInfo);
        }
    }

    /**
     * 群发自定义消息
     */
    public static void batchSendInfo(String wsInfo, List<String> ids) {
        for (String userId : ids) {
            sendInfo(wsInfo, userId);
        }
    }

    /**
     * 群发所有人
     */
    public static void batchSendInfo(String wsInfo) {
        websocketMap.forEach((k, v) -> v.sendMessage(wsInfo));
    }

    /**
     * 获取当前连接信息
     */
    public static List<String> getIds() {
        return new ArrayList<>(websocketMap.keySet());
    }


}
