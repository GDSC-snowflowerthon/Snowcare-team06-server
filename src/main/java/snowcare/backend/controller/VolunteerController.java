package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.service.VolunteerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;

    // 봉사활동 구인글 전체 조회
    @GetMapping()
    public ResponseEntity<List<VolunteerResponse>> getAllVolunteers(@RequestParam(value = "userId") Long userId) {
        List<VolunteerResponse> responses = volunteerService.getAllVolunteers(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    // 봉사활동 구인글 상세 조회



}
