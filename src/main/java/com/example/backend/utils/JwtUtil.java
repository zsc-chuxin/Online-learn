package com.example.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class JwtUtil {
    private static final String secret = "hand2020";
    private static final Long expiration = 3600L;

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(String name) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expiration * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                //可以将基本信息放到claims中
                //userName
                .withClaim("userName", name)
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //SECRET加密
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 校验token并解析token
     */
    public static boolean verifyToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            if (jwt.getExpiresAt().before(new Date())) {
                System.out.println("token过期");
                return false;
            }
        } catch (Exception e) {
            //解码异常则抛出异常
            System.out.println("token解析异常:" + e.getMessage());
            return false;
        }
        return true;
    }

}