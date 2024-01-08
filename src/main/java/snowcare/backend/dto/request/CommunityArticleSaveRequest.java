package snowcare.backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityArticleSaveRequest {
    private Long userId;
    private String title;
    private String content;
    private String image;
}
