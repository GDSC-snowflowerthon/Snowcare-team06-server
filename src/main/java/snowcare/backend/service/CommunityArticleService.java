package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.domain.User;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.repository.CommunityArticleRepository;
import snowcare.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommunityArticleService {
    private final CommunityArticleRepository communityArticleRepository;
    private final UserRepository userRepository;

    // 커뮤니티 글 전체 조회
    public List<CommunityArticleResponse> getAllCommunityArticles() {
        List<CommunityArticle> communityArticles = communityArticleRepository.findAll();
        return communityArticles.stream()
                .map(m -> CommunityArticleResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .userImage(m.getUser().getProfileImage())
                        .communityArticleId(m.getId())
                        .createdDate(m.getCreatedDate())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(m.getImage())
                        .likeCount(m.getLikeCount())
                        .build())
                .collect(Collectors.toList());
    }

    // 커뮤니티 글 상세 조회
    public CommunityArticleResponse getCommunityArticleById(Long communityArticleId) {
        CommunityArticle communityArticle = communityArticleRepository.findById(communityArticleId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITYARTICLE));
        return CommunityArticleResponse.builder()
                .userNickname(communityArticle.getUser().getNickname())
                .userImage(communityArticle.getUser().getProfileImage())
                .communityArticleId(communityArticle.getId())
                .createdDate(communityArticle.getCreatedDate())
                .title(communityArticle.getTitle())
                .content(communityArticle.getContent())
                .image(communityArticle.getImage())
                .likeCount(communityArticle.getLikeCount())
                .build();
    }

    // 커뮤니티 글 작성
    public Long addCommunityArticle(CommunityArticleSaveRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        CommunityArticle communityArticle = CommunityArticle.createCommunityArticle(request, user);
        CommunityArticle savedCommunityArticle = communityArticleRepository.save(communityArticle);
        return savedCommunityArticle.getId();
    }

    // 커뮤니티 글 수정
    public Long updateCommunityArticle(Long communityArticleId, CommunityArticleSaveRequest request) {
        CommunityArticle communityArticle = communityArticleRepository.findById(communityArticleId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITYARTICLE));
        communityArticle.updateCommunityArticle(request);
        return communityArticle.getId();
    }

    // 커뮤니티 글 삭제
    public void deleteCommunityArticle(Long communityArticleId) {
        communityArticleRepository.deleteById(communityArticleId);
    }
}
