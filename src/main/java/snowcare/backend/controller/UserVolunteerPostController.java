package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.domain.UserVolunteerPost;
import snowcare.backend.dto.response.UserVolunteerPostResponse;
import snowcare.backend.service.UserVolunteerPostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/record")
public class UserVolunteerPostController {
    private final UserVolunteerPostService userVolunteerPostService;

    // 내 봉사활동 기록하기 전체 조회
    @GetMapping()
    public ResponseEntity<List<UserVolunteerPostResponse>> getAllUserVolunteerPosts(@RequestParam(value = "userId") Long userId) {
        List<UserVolunteerPostResponse> responses = userVolunteerPostService.getAllUserVolunteerPosts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    /*


     */
}
