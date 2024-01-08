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
public class CommentCommunityArticle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_community_article_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_article_id")
    private CommunityArticle communityArticle;

    private String content;

    // 연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getCommentCommunityArticles().add(this);
    }

    // 생성 메서드
    public static CommentCommunityArticle createCommentCommunityArticle(User user, CommunityArticle communityArticle, String content) {
        CommentCommunityArticle commentCommunityArticle = new CommentCommunityArticle();
        commentCommunityArticle.setUser(user);
        commentCommunityArticle.setCommunityArticle(communityArticle);
        commentCommunityArticle.setContent(content);

        return commentCommunityArticle;
    }
}
