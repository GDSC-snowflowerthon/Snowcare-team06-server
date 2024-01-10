package snowcare.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.dto.request.ProfileImageChangeRequest;
import snowcare.backend.dto.request.UserSettingChangeRequest;
import snowcare.backend.dto.response.UserResponse;
import snowcare.backend.service.UserService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    // 회원 정보 조회
    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long userId) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 프로필 이미지 수정
    @PatchMapping("profile-image")
    public ResponseEntity<Void> changeProfileImage(@Valid ProfileImageChangeRequest request) throws IOException {
        userService.changeProfileImage(request.getUserId(), request.getImage());
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 중복 조회
    @GetMapping("nickname/{nickname}")
    public ResponseEntity<Void> checkNicknameDuplicate(@PathVariable("nickname") String nickname) {
        userService.checkNicknameDuplicate(nickname);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 변경
    @PatchMapping("nickname")
    public ResponseEntity<Void> changeUserNickname(@RequestParam(value = "userId") Long userId,
                                                   @RequestParam(value = "nickname") String nickname) {
        userService.changeUserNickname(userId, nickname);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 설정 변경
    @PatchMapping("setting")
    public ResponseEntity<Void> changeUserSetting(@Valid UserSettingChangeRequest request) {
        userService.changeUserSetting(request);
        return new ResponseEntity(HttpStatus.OK);
    }
}
