package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

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
}