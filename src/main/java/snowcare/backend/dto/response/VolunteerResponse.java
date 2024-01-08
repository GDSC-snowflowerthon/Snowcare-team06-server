package snowcare.backend.dto.response;

import lombok.*;

@Getter
@Builder
public class VolunteerResponse {
    private Long volunteerId;
    private Long userId;
    private String title;
    private String content;
    private String image;
    private String place;
}
