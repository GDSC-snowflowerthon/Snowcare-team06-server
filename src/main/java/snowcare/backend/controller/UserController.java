package snowcare.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.dto.request.ProfileImageChangeRequest;
import snowcare.backend.service.UserService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    // 회원 프로필 이미지 수정
    @PatchMapping("profile-image")
    public ResponseEntity<Void> changeProfileImage(@Valid ProfileImageChangeRequest request) throws IOException {
        userService.changeProfileImage(request.getUserId(), request.getImage());
        return new ResponseEntity(HttpStatus.OK);
    }
}
