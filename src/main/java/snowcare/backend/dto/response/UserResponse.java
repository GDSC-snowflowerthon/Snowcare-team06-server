package snowcare.backend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;
    private String region;
    private Boolean weatherAlarm;
    private Boolean newVolunteerAlarm;
}
