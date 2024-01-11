package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.common.oauth.dto.AccessTokenResponse;
import snowcare.backend.common.oauth.dto.AuthTokens;
import snowcare.backend.common.oauth.platform.kakao.KakaoLoginParams;
import snowcare.backend.service.AuthService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) { // Auth code
        return ResponseEntity.ok(authService.login(params));
    }

    // 로그아웃 시 RefreshToken 삭제
    @PostMapping("/kakao/logout")
    public ResponseEntity<Void> logoutKakao(@RequestBody Map<String, String> accessToken) {
        authService.logout(accessToken.get("accessToken"));
        return new ResponseEntity(HttpStatus.OK);
    }

    // refresh token으로 accessToken 재발급
    @PostMapping("refresh-token")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody Map<String, String> refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken.get("refreshToken")));
    }
}
