package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.oauth.*;
import snowcare.backend.common.oauth.dto.AccessTokenResponse;
import snowcare.backend.common.oauth.dto.AuthTokens;
import snowcare.backend.domain.User;
import snowcare.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = findOrCreateUser(oAuthInfoResponse, params);
        return authTokensGenerator.generate(userId, oAuthInfoResponse.getEmail());
    }

    private Long findOrCreateUser(OAuthInfoResponse oAuthInfoResponse, OAuthLoginParams extraParams) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(oAuthInfoResponse, extraParams));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse, OAuthLoginParams extraParams) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(extraParams.getNickname())
                .region(extraParams.getRegion())
                .weatherAlarm(extraParams.getWeatherAlarm())
                .newVolunteerAlarm(extraParams.getNewVolunteerAlarm())
                //.oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getId();
    }

    public void logout(String accessToken){
        authTokensGenerator.deleteRefreshToken(accessToken);
    }

    public AccessTokenResponse refreshToken(String refreshToken) {
        return authTokensGenerator.accessTokenByRefreshToken(refreshToken);
    }
}
