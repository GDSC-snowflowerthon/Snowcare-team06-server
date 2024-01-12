package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.common.jwt.JwtUtils;
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
    private final JwtUtils jwtUtils;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Map<String, Object> idAndIfNew = findOrCreateUser(oAuthInfoResponse);
        Long userId = (Long) idAndIfNew.get("userId");
        Boolean newUser = (Boolean) idAndIfNew.get("newUser");
        AuthTokens authTokens = jwtUtils.createToken(userId, oAuthInfoResponse.getEmail());// Save User token
        authTokens.setNewUser(newUser);
        return authTokens;
    }

    private Map<String, Object> findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        Map<String, Object> idAndIfNew = new HashMap<>();
        Boolean newUser = false;
        Long userId = null;
        User user = userRepository.findByEmail(oAuthInfoResponse.getEmail());
        if (user == null){
            userId = newUser(oAuthInfoResponse);
            newUser = true;
        } else{
            userId = user.getId();
        }
        idAndIfNew.put("userId", userId);
        idAndIfNew.put("newUser", newUser);
        return idAndIfNew;
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .build();

        return userRepository.save(user).getId();
    }

    public void logout(String accessToken){
        jwtUtils.deleteRefreshToken(accessToken);
    }


    // accessToken 재발급
    public AccessTokenResponse refreshToken(String refresh) {

        String refreshToken = refresh.replace("Bearer ", "");

        // refresh 토큰 유효한지 확인
        jwtUtils.validateRefreshToken(refreshToken);
        Long userId = jwtUtils.getUserIdFromToken(refreshToken);
        User findUser = getUserOrThrow(userId);
        String createdAccessToken = jwtUtils.recreateAccessToken(findUser);

        AccessTokenResponse response = AccessTokenResponse.builder()
                .accessToken(createdAccessToken)
                .build();

        return response;
    }

    // 예외 처리 - 존재하는 user인지
    public User getUserOrThrow(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
