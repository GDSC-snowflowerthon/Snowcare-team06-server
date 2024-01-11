package snowcare.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.dto.request.VolunteerSaveRequest;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.service.VolunteerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/{volunteerId}")
    public ResponseEntity<VolunteerResponse> getVolunteerById(@PathVariable("volunteerId") Long volunteerId,
                                                              @RequestParam(value = "userId") Long userId) {
        VolunteerResponse response = volunteerService.getVolunteerById(volunteerId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 봉사활동 구인글 최신 3개 조회
    @GetMapping("/recent")
    public ResponseEntity<List<VolunteerResponse>> getThreeVolunteers(@RequestParam(value = "userId") Long userId) {
        List<VolunteerResponse> responses = volunteerService.getThreeVolunteers(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    // 봉사활동 구인글 작성
    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addVolunteer(@Valid VolunteerSaveRequest request) throws IOException {
        Long volunteerId = volunteerService.addVolunteer(request);
        Map<String, Long> response = new HashMap<>();
        response.put("volunteerId", volunteerId);
        return ResponseEntity.ok(response);
    }

    // 봉사활동 구인글 삭제
    @DeleteMapping("/delete/{volunteerId}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable("volunteerId") Long volunteerId) {
        volunteerService.deleteVolunteer(volunteerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
