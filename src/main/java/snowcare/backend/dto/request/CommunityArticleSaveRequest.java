package snowcare.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityArticleSaveRequest {
    @NotNull
    private Long userId;
    private String title;
    private String content;
    private MultipartFile image;
}
