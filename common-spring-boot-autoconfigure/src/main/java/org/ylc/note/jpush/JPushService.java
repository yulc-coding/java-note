package org.ylc.note.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 极光推送业务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/24
 */
public class JPushService {

    private Logger logger = LoggerFactory.getLogger(JPushService.class);

    /**
     * 认证码
     */
    private String authCode;

    public JPushService(String authCode) {
        this.authCode = authCode;
    }

    /**
     * 按别名推送
     *
     * @param pushEntity 内容
     * @param alias      别名（集合形式）
     */
    public void sendWithAlias(JPushEntity pushEntity, List<String> alias) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.alias(alias)).build();
        pushInfo(pushPayload);
    }

    /**
     * 按别名推送
     *
     * @param pushEntity 内容
     * @param alias      别名（数组形式）
     */
    public void sendWithAlias(JPushEntity pushEntity, String... alias) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.alias(alias)).build();
        pushInfo(pushPayload);
    }

    /**
     * 按标签发送 集合
     *
     * @param pushEntity 内容
     * @param tags       标签（集合方式）
     */
    public void sendWithTags(JPushEntity pushEntity, List<String> tags) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.tag(tags)).build();
        pushInfo(pushPayload);
    }

    /**
     * 按标签发送 数组
     *
     * @param pushEntity 内容
     * @param tags       标签（数组方式）
     */
    public void sendWithTags(JPushEntity pushEntity, String... tags) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.tag(tags)).build();
        pushInfo(pushPayload);
    }

    /**
     * 给指定标签的别名发送
     *
     * @param pushEntity 内容
     * @param tag        标签
     * @param alias      别名（数组形式）
     */
    public void sendPushWithForTag(JPushEntity pushEntity, String[] tag, String... alias) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.tag(tag)).setAudience(Audience.alias(alias)).build();
        pushInfo(pushPayload);
    }

    /**
     * 给指定标签的别名发送
     *
     * @param pushEntity 内容
     * @param tag        标签
     * @param alias      别名（集合形式）
     */
    public void sendPushWithForTag(JPushEntity pushEntity, String[] tag, List<String> alias) {
        PushPayload.Builder builder = initBuild(pushEntity);
        PushPayload pushPayload = builder.setAudience(Audience.tag(tag)).setAudience(Audience.alias(alias)).build();
        pushInfo(pushPayload);
    }

    /**
     * 初始化构造器
     *
     * @param JPushEntity 推送信息
     */
    private PushPayload.Builder initBuild(JPushEntity JPushEntity) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setNotification(Notification.newBuilder()
                        .setAlert(JPushEntity.getContent())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(JPushEntity.getTitle())
                                .addExtras(JPushEntity.getExtras())
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(JPushEntity.getExtras()).build()
                        )
                        .build()
                );
    }

    /**
     * 发起推送
     */
    private void pushInfo(PushPayload payload) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setApnsProduction(true);
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(authCode, null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, responseWrapper -> {
                if (logger.isDebugEnabled()) {
                    logger.debug("Got result: " + responseWrapper.responseContent);
                }
            });
            // 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
            client.close();
        } catch (URISyntaxException e) {
            logger.error("极光推送失败，{}", e.getMessage());
            e.printStackTrace();
        }
    }


}
