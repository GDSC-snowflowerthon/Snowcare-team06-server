package snowcare.backend.common.oauth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, Object> makeBody();
    String getRegion();
    String getNickname();
    Boolean getWeatherAlarm();
    Boolean getNewVolunteerAlarm();
}
