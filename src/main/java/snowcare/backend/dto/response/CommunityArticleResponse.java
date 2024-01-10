package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile image;
    private int likeCount;
}
