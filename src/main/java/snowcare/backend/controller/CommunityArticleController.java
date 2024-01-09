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

import java.util.List;

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
    public ResponseEntity<CommunityArticleResponse> getCommunityArticleById(@PathVariable("communityArticleId") Long id) {
            CommunityArticle communityArticle = communityArticleService.getCommunityArticleById(id);

            if (communityArticle != null) {
                CommunityArticleResponse response = CommunityArticleResponse.builder()
                        .userNickname(communityArticle.getUser().getNickname())
                        .userImage(communityArticle.getUser().getProfileImage())
                        .communityArticleId(communityArticle.getId())
                        .createdDate(communityArticle.getCreatedDate())
                        .title(communityArticle.getTitle())
                        .content(communityArticle.getContent())
                        .image(communityArticle.getImage())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    // 커뮤니티 글 작성
    @PostMapping("/new")
    public ResponseEntity<Long> addCommunityArticle(@RequestBody CommunityArticleSaveRequest request) {
        Long communityArticleId = communityArticleService.addCommunityArticle(request);
        return ResponseEntity.ok(communityArticleId);
    }

    // 커뮤니티 글 수정
    @PatchMapping("/edit/{communityArticleId}")
    public ResponseEntity<Long> updateCommunityArticle(@PathVariable("communityArticleId") Long id, @RequestBody CommunityArticleSaveRequest request) {
        Long communityArticleId = communityArticleService.updateCommunityArticle(id, request);
        return ResponseEntity.ok(communityArticleId);
    }

    // 커뮤니티 글 삭제
    @DeleteMapping("/delete/{communityArticleId}")
    public void deleteCommunityArticle(@PathVariable("communityArticleId") Long id) {
        communityArticleService.deleteCommunityArticle(id);
    }
}
