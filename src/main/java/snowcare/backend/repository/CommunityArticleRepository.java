package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snowcare.backend.domain.CommunityArticle;

@Repository
public interface CommunityArticleRepository extends JpaRepository<CommunityArticle, Long> {
}
