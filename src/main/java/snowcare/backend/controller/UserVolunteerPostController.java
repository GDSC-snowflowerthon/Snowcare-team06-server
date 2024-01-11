package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.domain.UserVolunteerPost;
import snowcare.backend.dto.request.UserVolunteerPostSaveRequest;
import snowcare.backend.dto.response.UserVolunteerPostResponse;
import snowcare.backend.service.UserVolunteerPostService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/record")
public class UserVolunteerPostController {
    private final UserVolunteerPostService userVolunteerPostService;

    // 나의 봉사활동 기록하기 전체 조회
    @GetMapping()
    public ResponseEntity<List<UserVolunteerPostResponse>> getAllUserVolunteerPosts(@RequestParam(value = "userId") Long userId) {
        List<UserVolunteerPostResponse> responses = userVolunteerPostService.getAllUserVolunteerPosts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    // 나의 봉사활동 기록하기 상세 조회
    @GetMapping("{userVolunteerPostId}")
    public ResponseEntity<UserVolunteerPostResponse> getUserVolunteerPostById(@PathVariable("userVolunteerPostId") Long userVolunteerPostId,
                                                                              @RequestParam(value = "userId") Long userId) {
        UserVolunteerPostResponse response = userVolunteerPostService.getUserVolunteerPostById(userVolunteerPostId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 나의 봉사활동 기록하기 작성
    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addUserVolunteerPost(UserVolunteerPostSaveRequest request) throws IOException {
        Long userVolunteerPostId = userVolunteerPostService.addUserVolunteerPost(request);
        Map<String, Long> response = new HashMap<>();
        response.put("userVolunteerPostId", userVolunteerPostId);
        return ResponseEntity.ok(response);
    }


    /*

     */
}
