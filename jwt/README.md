# 基于JWT（JSON Web Token）的token身份验证

### 详细介绍
[基于JWT（JSON Web Token）的token身份验证](https://juejin.im/post/5ddf7f5ff265da05df4fbd1f)

### 格式：
* Header 头信息
```
{
  "alg": "Algorithm  加密方法：HS256",
  "cty": "Content Type ",
  "typ": "Type" ,
  "kid": "Key Id"
 }
```
* Payload  载体信息：数据包放在这里
```
{
  "iss": "Issuer JWT的签发者",
  "aud": "Audience 接收JWT的一方",
  "sub": "Subject JWT的主题",
  "exp": "Expiration Time JWT的过期时间",
  "nbf": "Not Before 在xxx之间，该JWT都是可用的",
  "iat": "Issued At 该JWT签发的时间",
  "jti": "JWT ID JWT的唯一身份标识",
  "xxx": "自定义属性"
}
```
* Signature 签名信息 = 加密算法(header + "." + payload, 密钥)

* TOKEN
```
base64(Header).base64(Payload).Signature
```