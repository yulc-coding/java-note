package org.ylc.note.jwt;

import cn.hutool.core.date.DateUtil;
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
import java.util.Date;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 普通的 jwt
 * <p>
 * 头信息：
 * Header  Base64Url encoded
 * {
 * "alg": "Algorithm  加密方法：HS256",
 * "cty": "Content Type ",
 * "typ": "Type" ,
 * "kid": "Key Id"
 * }
 * <p>
 * 载体信息：数据包放在这里
 * Payload  Base64Url encoded
 * {
 * "iss": "Issuer JWT的签发者",
 * "aud": "Audience 接收JWT的一方",
 * "sub": "Subject JWT的主题",
 * "exp": "Expiration Time JWT的过期时间",
 * "nbf": "Not Before 在xxx之间，该JWT都是可用的",
 * "iat": "Issued At 该JWT签发的时间",
 * "jti": "JWT ID JWT的唯一身份标识",
 * "xxx": "自定义属性"
 * }
 * <p>
 * 签名信息，该签名信息是通过header和payload，加上secret，通过算法加密生成，用于校验token防串改：
 * Signature  = 加密算法(header + "." + payload, 密钥)
 * <p>
 * 最终生成token 格式 base64(Header).base64(Payload).Signature
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/25
 */
public class NormalJwtUtil {

    /**
     * 签名秘钥
     */
    private static final String SECRET = "!@#$%YLC*&^%()95622SSxx";

    /**
     * 创建token
     *
     * @param json 需要放入token的参数，多个参数可以封装成json或者map
     * @return token
     */
    public static String createToken(JSONObject json) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withSubject(json.toString())
                    .withIssuer("ylc")
                    // 设置过期时间为1分钟后
                    .withExpiresAt(DateUtil.offsetMinute(new Date(), 1))
                    .withClaim("customString", "自定义参数")
                    .withArrayClaim("customArray", new Integer[]{1, 2, 3})
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
                    // 验证签发人是否相同
                    .withIssuer("ylc")
                    .build();
            /*
             * 校验：
             * 格式校验：header.payload.signature
             * 加密方式校验 Header中的alg
             * 签名信息校验，防串改
             * 载体Payload 中公有声明字段校验
             */
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
            Claim customStringClaim = claims.get("customString");
            Claim customArrayClaim = claims.get("customArray");

            String issuer = jwt.getIssuer();
            String subject = jwt.getSubject();

            System.out.println(customStringClaim.asString());
            System.out.println(Arrays.toString(customArrayClaim.asArray(Integer.class)));
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
        subjectJson.put("name", "ylc");

        String token = createToken(subjectJson);
        System.out.println("token:" + token);
        System.out.println("==============================================");

        System.out.println("1 min exp,now verify result:" + verifyToke(token));
        System.out.println("==============================================");

        System.out.println("decode info:");
        decodeToken(token);

        String expToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJuYW1lXCI6XCJ5bGNcIixcInVzZXJJZFwiOjg4ODh9IiwiaXNzIjoieWxjIiwiZXhwIjoxNTc0OTI5MjA1LCJjdXN0b21BcnJheSI6WzEsMiwzXSwiY3VzdG9tU3RyaW5nIjoi6Ieq5a6a5LmJ5Y-C5pWwIn0.hMVNSj8QzW9kyd7dX97IimXx3YvMM1yP91UiavgvzwM";
        verifyToke(expToken);
    }


}
