package com.jdicity.gateway.util;

import com.jdicity.gateway.constant.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类。生成的token，可以通过base64进行解密出明文信息。
 * @author sunjianzhou
 * @date 2020-12-10 14:48
 */
@Slf4j
public class JWTUtils {

    /**
     * JWT token生成。
     * @param appKey appKey
     * @return 返回新生成的token。
     */
    public static String generateJsonWebToken(String appKey) {

        String token = Jwts.builder().setSubject(TokenProperties.SUBJECT)
                .claim(TokenProperties.APP_KEY, appKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TokenProperties.EXPIRE))
                .signWith(SignatureAlgorithm.HS256, TokenProperties.SECRET).compact();

        token = TokenProperties.TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 解析token。
     * @param token 待校验的token。
     * @return 返回claim。
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(TokenProperties.SECRET)
                    .parseClaimsJws(token.replace(TokenProperties.TOKEN_PREFIX, "")).getBody();
        } catch (Exception e) {
            log.info("token解析错误:{}", token);
            return null;
        }

    }

}
