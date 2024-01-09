package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.service.CommunityArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/community")
public class CommunityArticleController {
    private final CommunityArticleService communityArticleService;

    // 커뮤니티 글 전체 조회
    @GetMapping()
    public List<CommunityArticleResponse> getAllCommunityArticles() {
        return communityArticleService.getAllCommunityArticles();
    }

    // 커뮤니티 글 상세 조회
    @GetMapping("/{communityArticleId}")
    public ResponseEntity<CommunityArticleResponse> getCommunityArticleById(@PathVariable("communityArticleId") Long communityArticleId) {
            CommunityArticleResponse response = communityArticleService.getCommunityArticleById(communityArticleId);

            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    // 커뮤니티 글 작성
    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addCommunityArticle(@RequestBody CommunityArticleSaveRequest request) {
        Long communityArticleId = communityArticleService.addCommunityArticle(request);
        Map<String, Long> response = new HashMap<>();
        response.put("communityArticleId", communityArticleId);
        return ResponseEntity.ok(response);
    }

    // 커뮤니티 글 수정
    @PatchMapping("/edit/{communityArticleId}")
    public ResponseEntity<Map<String, Long>> updateCommunityArticle(@PathVariable("communityArticleId") Long communityArticleId, @RequestBody CommunityArticleSaveRequest request) {
        Long responseCommunityArticleId = communityArticleService.updateCommunityArticle(communityArticleId, request);
        Map<String, Long> response = new HashMap<>();
        response.put("communityArticleId", responseCommunityArticleId);
        return ResponseEntity.ok(response);
    }

    // 커뮤니티 글 삭제
    @DeleteMapping("/delete/{communityArticleId}")
    public void deleteCommunityArticle(@PathVariable("communityArticleId") Long communityArticleId) {
        communityArticleService.deleteCommunityArticle(communityArticleId);
    }
}
