package snowcare.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerSaveRequest {
    private String title;
    private String content;
    private String place;
    private Long userId;
    private MultipartFile image;
}
