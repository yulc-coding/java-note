package org.ylc.note.security.constant;

/**
 * 系统配置常量
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
public class ConfigConstants {

    /**
     * 请求返回
     */
    public static class Return {
        /**
         * 成功
         */
        public static final int SUCCESS = 200;
        /**
         * 操作失败，统一返回代码编号，直接打印出msg信息
         */
        public static final int OPERATION_FAILED = 500;
        /**
         * 没有访问权限,提示非法操作
         */
        public static final int ACCESS_RESTRICTED = 403;
        /**
         * 认证失败token过期、或者没有传token，引导到登录界面
         */
        public static final int AUTHENTICATION_FAIL = 401;
    }

}
