package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CommunityArticleResponse {
    private String userNickname;
    private String userImage;
    private Long communityArticleId;
    private LocalDate createdDate;
    private String title;
    private String content;
    private String image;
    private int likeCount;
}
