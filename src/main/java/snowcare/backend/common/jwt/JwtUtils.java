package snowcare.backend.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import snowcare.backend.common.RedisUtil;
import snowcare.backend.common.oauth.dto.AuthTokens;
import snowcare.backend.domain.User;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final RedisUtil redisUtil;

    @Value("${jwt.secret}")
    private String secretKey;

    private long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30; // 30 분
    private long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private Key key;

//    public JwtUtils(String secret) {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    // 토큰 생성
    public AuthTokens createToken(Long userId, String email) {

        String accessToken = JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("email", email)
                .withClaim("role", "ROLE_USER")
                .sign(Algorithm.HMAC512(secretKey));

        String refreshToken = JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withClaim("email", email)
                .withClaim("role", "ROLE_USER")
                .sign(Algorithm.HMAC512(secretKey));

        // redis
        Date refreshTokenExpiredAt = new Date(System.currentTimeMillis()  + REFRESH_TOKEN_EXPIRATION_TIME);
        redisUtil.setDataExpire(String.valueOf(userId), refreshToken, refreshTokenExpiredAt.getTime());

        return AuthTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }

    // Access 토큰 재생성
    public String recreateAccessToken(User user) {

        String data = redisUtil.getData(user.getId().toString());
        if (data == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        redisUtil.deleteData(user.getId().toString());

        String accessToken = JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(secretKey));

        return accessToken;
    }

    public Long getUserIdFromToken(String token) {

        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        return Long.parseLong(jwt.getSubject());
    }

    public Map<String, Claim> getClaimsFromToken(String token) {

        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        return jwt.getClaims();
    }

    // refresh 토큰의 유효성 + 만료일자 확인 -> 유효하면 true 리턴
    public Boolean validateRefreshToken(String token) {

        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            System.out.println(">> JwtException: " + "TokenExpiredException");
            throw new JwtException("TOKEN_EXPIRED");
        } catch (JWTVerificationException e) {
            System.out.println(">> JwtException: " + "JWTVerificationException");
            throw new JwtException("TOKEN_INVALID");
        }
    }

    // 토큰의 유효성 + 만료일자 확인
    public Boolean validateAccessToken(String token) {

        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;

        } catch (TokenExpiredException e) {
            System.out.println(">> JwtException: " + "TokenExpiredException");
            throw new JwtException("TOKEN_EXPIRED");
        } catch (JWTVerificationException e) {
            System.out.println(">> JwtException: " + "JWTVerificationException");
            throw new JwtException("TOKEN_INVALID");
        }
    }

    public void deleteRefreshToken(String accessToken) {
        String userId = getUserIdFromToken(accessToken).toString();
        String data = redisUtil.getData(userId);
        if (data == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        redisUtil.deleteData(userId);
    }
}
