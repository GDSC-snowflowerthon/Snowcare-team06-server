package snowcare.backend.common.oauth.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens { // 사용자에게 내려주는 서비스의 인증 토큰 값
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private Boolean newUser;

    public static AuthTokens of(String accessToken, String refreshToken, Long userId, Boolean newUser) {
        return new AuthTokens(accessToken, refreshToken,  userId, newUser);
    }
}
