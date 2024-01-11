package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserVolunteerPostResponse {
    private String title;
    private String content;
    private String image;
    private String userVolunteerDate;
}
