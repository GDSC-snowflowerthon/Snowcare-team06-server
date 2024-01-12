package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.CommentCommunityArticle;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.domain.User;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.dto.response.CommentResponse;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.repository.CommentCommunityArticleRepository;
import snowcare.backend.repository.CommunityArticleRepository;
import snowcare.backend.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommunityArticleService {
    private final CommunityArticleRepository communityArticleRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final LikeService likeService;
    private final CommentCommunityArticleRepository commentCommunityArticleRepository;

    // 커뮤니티 글 전체 조회
    public List<CommunityArticleResponse> getAllCommunityArticles(Long userId) {
        List<CommunityArticle> communityArticles = communityArticleRepository.findAll();
        return communityArticles.stream()
                .map(m -> CommunityArticleResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .userImage(m.getUser().getProfileImage())
                        .communityArticleId(m.getId())
                        .createdDate(m.getCreatedDate().toLocalDate())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(imageService.processImage(m.getImage()))
                        .likeCount(m.getLikeCount())
                        .userLiked(likeService.checkIfUserLikedCommunityArticle(userId, m.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    // 커뮤니티 글 상세 조회
    public CommunityArticleResponse getCommunityArticleById(Long communityArticleId, Long userId) {
        CommunityArticle communityArticle = getCommunityArticleOrThrow(communityArticleId);
        List<CommentCommunityArticle> commentCommunityArticles = commentCommunityArticleRepository.findByUserIdAndCommunityArticleId(userId, communityArticleId);
        List<CommentResponse> commentResponses = commentCommunityArticles.stream()
                .map(m -> CommentResponse.builder()
                        .commentId(m.getId())
                        .userNickname(m.getUser().getNickname())
                        .content(m.getContent())
                        .createdDate(m.getCreatedDate().toLocalDate())
                        .build())
                .collect(Collectors.toList());
        return CommunityArticleResponse.builder()
                .userNickname(communityArticle.getUser().getNickname())
                .userImage(communityArticle.getUser().getProfileImage())
                .communityArticleId(communityArticle.getId())
                .createdDate(communityArticle.getCreatedDate().toLocalDate())
                .title(communityArticle.getTitle())
                .content(communityArticle.getContent())
                .image(imageService.processImage(communityArticle.getImage()))
                .likeCount(communityArticle.getLikeCount())
                .userLiked(likeService.checkIfUserLikedCommunityArticle(userId, communityArticle.getId()))
                .comments(commentResponses)
                .build();
    }

    // 커뮤니티 글 작성
    public Long addCommunityArticle(CommunityArticleSaveRequest request) throws IOException {
        User user = getUserOrThrow(request.getUserId());
        String imageUUID = null;
        if(!request.getImage().isEmpty()){
            imageUUID = imageService.uploadImage(request.getImage());
        }
        CommunityArticle communityArticle = CommunityArticle.createCommunityArticle(request, user, imageUUID);
        communityArticleRepository.save(communityArticle);
        return communityArticle.getId();
    }

    // 커뮤니티 글 수정
    public void updateCommunityArticle(Long communityArticleId, CommunityArticleSaveRequest request) throws IOException {
        CommunityArticle communityArticle = getCommunityArticleOrThrow(communityArticleId);
        String imageUUID = null;
        if(!request.getImage().isEmpty()){
            imageUUID = imageService.uploadImage(request.getImage());
        }
        communityArticle.updateCommunityArticle(request.getTitle(), request.getContent(), imageUUID);
    }

    // 커뮤니티 글 삭제
    public void deleteCommunityArticle(Long communityArticleId) {
        getCommunityArticleOrThrow(communityArticleId);
        communityArticleRepository.deleteById(communityArticleId);
    }

    // 예외 처리 - 존재하는 User 인가
    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    // 예외 처리 - 존재하는 커뮤니티글인가
    private CommunityArticle getCommunityArticleOrThrow(Long id) {
        return communityArticleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITY_ARTICLE));
    }
}
