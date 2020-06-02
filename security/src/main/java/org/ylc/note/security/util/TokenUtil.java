package org.ylc.note.security.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 简单的Token操作类，也可以使用JWT去生成Token
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/26
 */
@Slf4j
public class TokenUtil {

    private static final byte[] SECRET_KEY = {54, 117, 81, -42, 68, 33, -128, -127, -79, 104, -29, -112, 104, -112, -93, 49};

    private static final SymmetricCrypto AES = new SymmetricCrypto(SymmetricAlgorithm.AES, SECRET_KEY);

    /**
     * 根据用户ID生成Token
     */
    public static String generator(String userId) {
        return AES.encryptHex(userId);
    }

    /**
     * 解析Token，返回用户ID
     */
    public static String parseToken(String token) {
        String userId = null;
        try {
            userId = AES.decryptStr(token, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            log.warn("Illegal Token: [{}]", token);
        }
        return userId;
    }

}
