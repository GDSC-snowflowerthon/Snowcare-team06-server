package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.CommentCommunityArticle;

import java.util.List;

public interface CommentCommunityArticleRepository extends JpaRepository<CommentCommunityArticle, Long> {
    List<CommentCommunityArticle> findByUserIdAndCommunityArticleId(Long userId, Long communityArticleId);
}
