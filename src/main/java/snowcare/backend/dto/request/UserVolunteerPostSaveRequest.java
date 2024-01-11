package snowcare.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVolunteerPostSaveRequest {
    @NotNull
    private Long userId;
    private String title;
    private String content;
    private MultipartFile image;
    @NotNull
    private LocalDate userVolunteerDate;
}
