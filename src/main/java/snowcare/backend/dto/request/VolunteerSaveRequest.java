package snowcare.backend.dto.request;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String place;
    @NotNull
    private Long userId;
    private MultipartFile image;
}
