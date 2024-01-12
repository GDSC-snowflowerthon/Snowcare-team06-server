package snowcare.backend.common.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens { // 사용자에게 내려주는 서비스의 인증 토큰 값
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;
    private Long userId;
    private Boolean newUser;

    public static AuthTokens of(String accessToken, String refreshToken, String grantType, Long expiresIn, Long userId, Boolean newUser) {
        return new AuthTokens(accessToken, refreshToken, grantType, expiresIn, userId, newUser);
    }
}
