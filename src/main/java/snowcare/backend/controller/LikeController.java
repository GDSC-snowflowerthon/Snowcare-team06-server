package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.service.LikeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    //  봉사활동글 좋아요 추가
    @PostMapping("/volunteer")
    public ResponseEntity<Void> addLikeVolunteer(@RequestParam(value = "userId") Long userId,
                                                  @RequestParam(value = "volunteerId") Long volunteerId) throws IOException {
        likeService.addLikeVolunteer(userId, volunteerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 봉사활동글 좋아요 삭제
    @DeleteMapping("/volunteer/del")
    public ResponseEntity<Void> deleteLikeVolunteer(@RequestParam(value = "userId") Long userId,
                                                  @RequestParam(value = "volunteerId") Long volunteerId) {
        likeService.deleteLikeVolunteer(userId, volunteerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저가 좋아요한 봉사활동글 조회
    @GetMapping("/volunteer/{userId}")
    public ResponseEntity<List<VolunteerResponse>> getUserLikedVolunteers(@PathVariable("userId") Long userId) {
        List<VolunteerResponse> response = likeService.getAllUserLikedVolunteers(userId);
        return ResponseEntity.ok().body(response);
    }

    // 커뮤니티글 좋아요 추가
    @PostMapping("/community")
    public ResponseEntity<Void> addLikeCommunityArticle(@RequestParam(value = "userId") Long userId,
                                                  @RequestParam(value = "communityArticleId") Long communityArticleId) throws IOException {
        likeService.addLikeCommunityArticle(userId, communityArticleId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티글 좋아요 삭제
    @DeleteMapping("/community/del")
    public ResponseEntity<Void> deleteLikeCommunityArticle(@RequestParam(value = "userId") Long userId,
                                                     @RequestParam(value = "communityArticleId") Long communityArticleId) {
        likeService.deleteLikeCommunityArticle(userId, communityArticleId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저가 좋아요한 커뮤니티글 조회
    @GetMapping("/community/{userId}")
    public ResponseEntity<List<CommunityArticleResponse>> getUserLikedCommunityArticles(@PathVariable("userId") Long userId) {
        List<CommunityArticleResponse> response = likeService.getAllUserLikedCommunityArticles(userId);
        return ResponseEntity.ok().body(response);
    }
}
