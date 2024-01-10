package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.LikeCommunityArticle;

import java.util.List;

public interface LikeCommunityArticleRepository extends JpaRepository<LikeCommunityArticle, Long> {
    LikeCommunityArticle findByUserIdAndCommunityArticleId(Long userId, Long communityArticleId);
    List<LikeCommunityArticle> findByUserId(Long userId);
}
