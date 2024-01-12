package snowcare.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserVolunteerPostResponse {
    private Long userVolunteerPostId;
    private String title;
    private String content;
    private String image;
    private LocalDate userVolunteerDate;
}
