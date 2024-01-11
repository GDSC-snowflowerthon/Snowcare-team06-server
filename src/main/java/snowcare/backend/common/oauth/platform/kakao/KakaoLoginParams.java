package snowcare.backend.common.oauth.platform.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import snowcare.backend.common.oauth.OAuthLoginParams;
import snowcare.backend.common.oauth.OAuthProvider;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    private String authorizationCode;
    // 추가 정보
    private String region;
    private Boolean weatherAlarm;
    private Boolean newVolunteerAlarm;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, Object> makeBody() {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("region", region);
        body.add("weatherAlarm", weatherAlarm);
        body.add("newVolunteerAlarm", newVolunteerAlarm);
        return body;
    }
}
