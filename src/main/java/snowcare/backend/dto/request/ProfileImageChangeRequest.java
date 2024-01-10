package snowcare.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileImageChangeRequest {
    @NotNull
    private Long userId;
    @NotNull
    private MultipartFile image;
}
