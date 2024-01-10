package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.CommunityArticle;

public interface CommunityArticleRepository extends JpaRepository<CommunityArticle, Long> {
}
