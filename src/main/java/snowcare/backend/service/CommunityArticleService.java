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
        List<CommunityArticleResponse> communityArticlesList = communityArticles.stream()
                .map(m -> CommunityArticleResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .userImage(m.getUser().getProfileImage())
                        .communityArticleId(m.getId())
                        .createdDate(m.getCreatedDate())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(m.getImage())
                        .build())
                .collect(Collectors.toList());

        return communityArticlesList;
    }

    // 커뮤니티 글 상세 조회
    public CommunityArticle getCommunityArticleById(Long id) {
        return communityArticleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }

    // 커뮤니티 글 작성
    public Long addCommunityArticle(CommunityArticleSaveRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        CommunityArticle communityArticle = CommunityArticle.createCommunityArticle(request, user);
        CommunityArticle savedCommunityArticle = communityArticleRepository.save(communityArticle);
        return savedCommunityArticle.getId();
    }

    // 커뮤니티 글 수정
    public Long updateCommunityArticle(Long id, CommunityArticleSaveRequest request) {
        CommunityArticle communityArticle = communityArticleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        communityArticle.updateCommunityArticle(request);
        return communityArticle.getId();
    }

    // 커뮤니티 글 삭제
    public void deleteCommunityArticle(Long id) {
        communityArticleRepository.deleteById(id);
    }
}
