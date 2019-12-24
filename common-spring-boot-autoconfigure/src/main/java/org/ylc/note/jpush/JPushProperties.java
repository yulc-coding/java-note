package org.ylc.note.jpush;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/24
 */
@ConfigurationProperties(prefix = "j-push")
public class JPushProperties {

    private String appKey;

    private String masterSecret;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

}
