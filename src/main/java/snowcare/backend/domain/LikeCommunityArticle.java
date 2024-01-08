package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import snowcare.backend.common.entity.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeCommunityArticle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_community_article_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_article_id")
    private CommunityArticle communityArticle;

    // 연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getLikeCommunityArticles().add(this);
    }

    // 생성 메서드
    public static LikeCommunityArticle createLikeCommunityArticle(User user, CommunityArticle communityArticle) {
        LikeCommunityArticle likeCommunityArticle = new LikeCommunityArticle();
        likeCommunityArticle.setUser(user);
        likeCommunityArticle.setCommunityArticle(communityArticle);

        return likeCommunityArticle;
    }
}
