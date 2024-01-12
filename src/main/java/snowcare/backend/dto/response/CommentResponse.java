package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private String userNickname;
    private String content;
    private LocalDate createdDate;
}
