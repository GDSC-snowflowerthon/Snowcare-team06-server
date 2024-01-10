package snowcare.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingChangeRequest {
    @NotNull
    private Long userId;
    private String region;
    @NotNull
    private Boolean newVolunteerAlarm;
    @NotNull
    private Boolean weatherAlarm;
}
