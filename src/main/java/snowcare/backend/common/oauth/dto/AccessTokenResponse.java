package snowcare.backend.common.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    private String accessToken;
    private String grantType;
    private Long expiresIn;

    public static AccessTokenResponse of(String accessToken, String grantType, Long expiresIn) {
        return new AccessTokenResponse(accessToken, grantType, expiresIn);
    }
}
