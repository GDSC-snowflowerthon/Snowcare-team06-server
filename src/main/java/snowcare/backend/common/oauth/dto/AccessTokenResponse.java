package snowcare.backend.common.oauth.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    private String accessToken;

    public static AccessTokenResponse of(String accessToken) {
        return new AccessTokenResponse(accessToken);
    }
}
