package org.ylc.note.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 对Payload进行 AES 加密
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/28
 */
public class AesJwtUtil {

    /**
     * 签名秘钥
     */
    private static final String SECRET = "!@#$%YLC*&^%()95622SSxx";

    /**
     * aes 加密
     * token中的信息加密
     * 其中秘钥随机生成
     * 所以每次系统重启后，之前加密的都会失效
     */
    private static final AES AES_ALGORITHM = SecureUtil.aes(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());

    /**
     * 创建token
     *
     * @param json 需要放入token的参数，多个参数可以封装成json或者map
     * @return token
     */
    public static String createToken(JSONObject json) {
        // 先进性AES加密
        String aesPayload = AES_ALGORITHM.encryptHex(json.toString());
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withSubject(aesPayload)
                    .withIssuer("AES")
                    // 设置过期时间为昨天，校验的时候回不通过
                    .withExpiresAt(DateUtil.yesterday())
                    .withClaim("customString", "payload进行AES加密")
                    .withArrayClaim("customArray", new String[]{"A", "E", "S"})
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            System.out.println(exception.getMessage());
            return null;
        }
    }

    /**
     * 校验token 合法性
     *
     * @param token to verify.
     */
    public static boolean verifyToke(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("AES")
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            System.out.println(exception.getMessage());
            return false;
        }
    }

    /**
     * 解析token
     *
     * @param token to decode.
     */
    public static void decodeToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim customArrayClaim = claims.get("customArray");
            Claim customStringClaim = claims.get("customString");

            String issuer = jwt.getIssuer();
            // 进行解密
            String subject = AES_ALGORITHM.decryptStr(jwt.getSubject());

            System.out.println(Arrays.toString(customArrayClaim.asArray(String.class)));
            System.out.println(customStringClaim.asString());
            System.out.println(issuer);
            System.out.println(JSONUtil.parseObj(subject));

        } catch (JWTDecodeException exception) {
            //Invalid token
            System.out.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        JSONObject subjectJson = new JSONObject();
        subjectJson.put("userId", 8888);
        subjectJson.put("name", "this is aes payload");

        String token = createToken(subjectJson);
        System.out.println("token:" + token);
        System.out.println("==============================================");

        System.out.println("exp is yesterday verify result:" + verifyToke(token));
        System.out.println("==============================================");

        System.out.println("decode info:");
        decodeToken(token);
    }
}
