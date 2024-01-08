package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.service.CommunityArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/community")
public class CommunityArticleController {
    private final CommunityArticleService communityArticleService;

    /*
    커뮤니티 글 전체 조회
     */
    @GetMapping()
    public List<CommunityArticle> getAllCommunityArticles() {
        return communityArticleService.getAllCommunityArticles();
    }

    /*
    커뮤니티 글 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommunityArticle> getCommunityArticleById(@PathVariable("id") Long id) {
            CommunityArticle communityArticle = communityArticleService.getCommunityArticleById(id);

            if (communityArticle != null) {
                return new ResponseEntity<>(communityArticle, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    /*
    커뮤니티 글 작성
     */
    @PostMapping("/new")
    public void addCommunityArticle(@RequestBody CommunityArticleSaveRequest request) {
        communityArticleService.addCommunityArticle(request);
    }

    /*
    커뮤니티 글 수정
     */
    @PatchMapping("/edit/{id}")
    public void updateCommunityArticle(@PathVariable("id") Long id, @RequestBody CommunityArticleSaveRequest request) {
        communityArticleService.updateCommunityArticle(id, request);
    }

    /*
    커뮤니티 글 삭제
     */
    @DeleteMapping("/delete/{id}")
    public void deleteCommunityArticle(@PathVariable("id") Long id) {
        communityArticleService.deleteCommunityArticle(id);
    }
}
