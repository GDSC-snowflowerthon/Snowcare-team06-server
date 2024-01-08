package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.domain.User;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.repository.CommunityArticleRepository;
import snowcare.backend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommunityArticleService {
    private final CommunityArticleRepository communityArticleRepository;
    private final UserRepository userRepository;

    /*
    커뮤니티 글 전체 조회
     */
    public List<CommunityArticle> getAllCommunityArticles() {
        return communityArticleRepository.findAll();
    }

    /*
    커뮤니티 글 상세 조회
     */
    public CommunityArticle getCommunityArticleById(Long id) {
        return communityArticleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /*
    커뮤니티 글 작성
     */
    public void addCommunityArticle(CommunityArticleSaveRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(RuntimeException::new);
        CommunityArticle communityArticle = CommunityArticle.createCommunityArticle(request, user);
        communityArticleRepository.save(communityArticle);
    }

    /*
    커뮤니티 글 수정
     */
    public void updateCommunityArticle(Long id, CommunityArticleSaveRequest request) {
        CommunityArticle communityArticle = communityArticleRepository.findById(id).orElseThrow(RuntimeException::new);
        communityArticle.updateCommunityArticle(request);
    }

    /*
    커뮤니티 글 삭제
     */
    public void deleteCommunityArticle(Long id) {
        communityArticleRepository.deleteById(id);
    }
}
