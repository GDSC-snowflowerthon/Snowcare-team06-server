package snowcare.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVolunteerPostSaveRequest {
    private Long userId;
    private String title;
    private String content;
    private MultipartFile image;
    private String userVolunteerDate;
}
