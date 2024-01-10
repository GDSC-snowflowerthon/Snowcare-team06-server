package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import snowcare.backend.common.entity.BaseEntity;
import snowcare.backend.dto.request.CommunityArticleSaveRequest;
import snowcare.backend.repository.UserRepository;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityArticle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_article_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;
    private String content;
    private String image;

    private int likeCount;

    // 생성 메서드
    public static CommunityArticle createCommunityArticle(CommunityArticleSaveRequest request, User user) {
        CommunityArticle communityArticle = CommunityArticle.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .likeCount(0)
                .build();
        return communityArticle;
    }

    public void updateCommunityArticle(CommunityArticleSaveRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.image = request.getImage();
    }
}
