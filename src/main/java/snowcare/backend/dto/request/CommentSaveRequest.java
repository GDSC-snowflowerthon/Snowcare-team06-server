package snowcare.backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSaveRequest {
    private Long userId;
    private Long postId;
    private String content;
}
