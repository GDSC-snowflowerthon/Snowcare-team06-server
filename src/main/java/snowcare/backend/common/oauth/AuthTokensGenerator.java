package snowcare.backend.common.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import snowcare.backend.common.RedisUtil;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.common.jwt.JwtTokenProvider;
import snowcare.backend.common.oauth.dto.AccessTokenResponse;
import snowcare.backend.common.oauth.dto.AuthTokens;
import snowcare.backend.domain.User;
import snowcare.backend.repository.UserRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator { // AuthTokens 을 발급해주는 클래스
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    public AuthTokens generate(Long userId, String email, Boolean newUser) { // userId (사용자 식별값) 을 받아 Access Token 을 생성
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt, email, user.getAuthority());
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt, email, user.getAuthority());

        redisUtil.setDataExpire(String.valueOf(userId), refreshToken, refreshTokenExpiredAt.getTime()); // 시간은 long 타입으로

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L, userId, newUser);
    }

    public Long extractUserId(String token) { // Access Token(또는 Refresh Token) 에서 userId (사용자 식별값) 추출
        return Long.valueOf(jwtTokenProvider.extractSubject(token));
    }

    public AccessTokenResponse accessTokenByRefreshToken(String refreshToken) {
        String userId = extractUserId(refreshToken).toString();
        String data = redisUtil.getData(userId);
        if (!data.equals(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REFRESH_TOKEN);
        }
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        String newAccessToken = jwtTokenProvider.generate(userId, accessTokenExpiredAt, user.getEmail(), user.getAuthority());
        return AccessTokenResponse.of(newAccessToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public void deleteRefreshToken(String accessToken) {
        String userId = extractUserId(accessToken).toString();
        String data = redisUtil.getData(userId);
        if (data == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        redisUtil.deleteData(userId);
    }
}
