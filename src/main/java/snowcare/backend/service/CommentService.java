package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.*;
import snowcare.backend.dto.request.CommentSaveRequest;
import snowcare.backend.repository.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    private final CommentVolunteerRepository commentVolunteerRepository;
    private final CommentCommunityArticleRepository commentCommunityArticleRepository;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final CommunityArticleRepository communityArticleRepository;

    // 봉사활동글 댓글 추가
    public void addCommentVolunteer(CommentSaveRequest request) throws IOException {
        User user =  getUserOrThrow(request.getUserId());
        Volunteer volunteer = getVolunteerOrThrow(request.getPostId());
        CommentVolunteer commentVolunteer = CommentVolunteer.createCommentVolunteer(user, volunteer, request.getContent());
        commentVolunteerRepository.save(commentVolunteer);
    }

    // 봉사활동글 댓글 삭제
    public void deleteCommentVolunteer(Long commentVolunteerId) {
        getCommentVolunteerOrThrow(commentVolunteerId);
        commentVolunteerRepository.deleteById(commentVolunteerId);
    }

    // 커뮤니티글 댓글 추가
    public void addCommentCommunityArticle(CommentSaveRequest request) throws IOException {
        User user =  getUserOrThrow(request.getUserId());
        CommunityArticle communityArticle = getCommunityArticleOrThrow(request.getPostId());
        CommentCommunityArticle commentCommunityArticle = CommentCommunityArticle.createCommentCommunityArticle(user, communityArticle, request.getContent());
        commentCommunityArticleRepository.save(commentCommunityArticle);
    }

    // 커뮤니티글 댓글 삭제
    public void deleteCommentCommunityArticle(Long commentCommunityArticleId) {
        getCommentCommunityArticleOrThrow(commentCommunityArticleId);
        commentCommunityArticleRepository.deleteById(commentCommunityArticleId);
    }

    // 예외 처리 - 존재하는 User 인가
    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    // 예외 처리 - 존재하는 Volunteer 인가
    private Volunteer getVolunteerOrThrow(Long volunteerId) {
        return volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOLUNTEER));
    }

    // 예외 처리 - 존재하는 CommunityArticle 인가
    private CommunityArticle getCommunityArticleOrThrow(Long communityArticleId) {
        return communityArticleRepository.findById(communityArticleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITY_ARTICLE));
    }

    // 예외 처리 - 존재하는 CommentVolunteer 인가
    private CommentVolunteer getCommentVolunteerOrThrow(Long commentVolunteerId) {
        return commentVolunteerRepository.findById(commentVolunteerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT_VOLUNTEER));
    }

    // 예외 처리 - 존재하는 CommentCommunityArticle 인가
    private CommentCommunityArticle getCommentCommunityArticleOrThrow(Long commentCommunityArticleId) {
        return commentCommunityArticleRepository.findById(commentCommunityArticleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT_COMMUNITY_ARTICLE));
    }

}
