package snowcare.backend.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
public class VolunteerResponse {
    private Long volunteerId;
    private String userNickname;
    private String userImage;
    private String title;
    private String content;
    private String image;
    private String place;
    private LocalDate createdDate;
}
