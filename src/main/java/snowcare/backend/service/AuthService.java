package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.oauth.*;
import snowcare.backend.common.oauth.dto.AccessTokenResponse;
import snowcare.backend.common.oauth.dto.AuthTokens;
import snowcare.backend.domain.User;
import snowcare.backend.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> idAndIfNew = findOrCreateUser(oAuthInfoResponse, params);
        Long userId = (Long) idAndIfNew.get("userId");
        Boolean newUser = (Boolean) idAndIfNew.get("newUser");
        return authTokensGenerator.generate(userId, oAuthInfoResponse.getEmail(), newUser);
    }

    private Map<String, Object> findOrCreateUser(OAuthInfoResponse oAuthInfoResponse, OAuthLoginParams extraParams) {
        Map<String, Object> idAndIfNew = new HashMap<>();
        Boolean newUser = false;
        Long userId = null;
        User user = userRepository.findByEmail(oAuthInfoResponse.getEmail());
        if (user == null){
            userId = newUser(oAuthInfoResponse, extraParams);
            newUser = true;
        } else{
            userId = user.getId();
        }
        idAndIfNew.put("userId", userId);
        idAndIfNew.put("newUser", newUser);
        return idAndIfNew;
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse, OAuthLoginParams extraParams) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
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
