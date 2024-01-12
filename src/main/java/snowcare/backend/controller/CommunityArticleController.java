package snowcare.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.common.SecurityUtil;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.service.CommunityArticleService;

import java.io.IOException;
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
    public ResponseEntity<List<CommunityArticleResponse>> getAllCommunityArticles(@RequestParam(value="userId") Long userId) {
        List<CommunityArticleResponse> response = communityArticleService.getAllCommunityArticles(userId);
        System.out.println("==== current userId: " + SecurityUtil.getCurrentUserId().toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 커뮤니티 글 상세 조회
    @GetMapping("/{communityArticleId}")
    public ResponseEntity<CommunityArticleResponse> getCommunityArticleById(@PathVariable("communityArticleId") Long communityArticleId,
                                                                            @RequestParam(value="userId") Long userId) {
        CommunityArticleResponse response = communityArticleService.getCommunityArticleById(communityArticleId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 커뮤니티 글 작성
    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addCommunityArticle(@Valid CommunityArticleSaveRequest request) throws IOException {
        Long communityArticleId = communityArticleService.addCommunityArticle(request);
        Map<String, Long> response = new HashMap<>();
        response.put("communityArticleId", communityArticleId);
        return ResponseEntity.ok(response);
    }

    // 커뮤니티 글 수정
    @PatchMapping("/edit/{communityArticleId}")
    public ResponseEntity<Void> updateCommunityArticle(@PathVariable("communityArticleId") Long communityArticleId,
                                                       @Valid CommunityArticleSaveRequest request) throws IOException {
        communityArticleService.updateCommunityArticle(communityArticleId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티 글 삭제
    @DeleteMapping("/delete/{communityArticleId}")
    public ResponseEntity<Void> deleteCommunityArticle(@PathVariable("communityArticleId") Long communityArticleId) {
        communityArticleService.deleteCommunityArticle(communityArticleId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
