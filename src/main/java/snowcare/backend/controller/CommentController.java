package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snowcare.backend.domain.CommentVolunteer;
import snowcare.backend.dto.request.CommentSaveRequest;
import snowcare.backend.service.CommentService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    // 봉사활동글 댓글 추가
    @PostMapping("/volunteer/{volunteerId}")
    public ResponseEntity<Void> addCommentVolunteer(@PathVariable("volunteerId") Long volunteerId,
                                                    @RequestParam(value = "userId") Long userId,
                                                    @RequestParam(value = "content") String content) throws IOException {
        commentService.addCommentVolunteer(userId, volunteerId, content);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 봉사활동글 댓글 삭제
    @DeleteMapping("/volunteer/{commentVolunteerId}")
    public ResponseEntity<Void> deleteCommentVolunteer(@PathVariable("commentVolunteerId") Long commentVolunteerId) {
        commentService.deleteCommentVolunteer(commentVolunteerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 커뮤니티글 댓글 추가
    @PostMapping("/community/{communityArticleId}")
    public ResponseEntity<Void> addCommentCommunityArticle(@PathVariable("communityArticleId") Long communityArticleId,
                                                           @RequestParam(value = "userId") Long userId,
                                                           @RequestParam(value = "content") String content) throws IOException {
        commentService.addCommentCommunityArticle(userId, communityArticleId, content);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티 글 댓글 삭제
    @DeleteMapping("/community/{commentCommunityArticleId}")
    public ResponseEntity<Void> deleteCommentCommunityArticle(@PathVariable("commentCommunityArticleId") Long commentCommunityArticleId) {
        commentService.deleteCommentCommunityArticle(commentCommunityArticleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
