package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import snowcare.backend.domain.CommunityArticle;
import snowcare.backend.domain.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommunityArticleResponse {
    private String userNickname;
    private String userImage;
    private Long communityArticleId;
    private LocalDateTime createdDate;
    private String title;
    private String content;
    private String image;

    public static CommunityArticleResponse responseCommunityArticleList(CommunityArticle communityArticle) {
        User user = communityArticle.getUser();
        CommunityArticleResponse response = CommunityArticleResponse.builder()
                .userNickname(user.getNickname())
                .userImage(user.getImage())
                .communityArticleId(communityArticle.getId())
                .createdDate(communityArticle.getCreatedDate())
                .title(communityArticle.getTitle())
                .content(communityArticle.getContent())
                .image(communityArticle.getImage())
                .build();
        return response;
    }
}
