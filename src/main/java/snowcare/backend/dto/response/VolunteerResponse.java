package snowcare.backend.dto.response;

import lombok.*;
import snowcare.backend.domain.CommentVolunteer;

import java.time.LocalDate;
import java.util.List;

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
    private int likeCount;
    private Boolean userLiked;
    private List<CommentResponse> comments;
}
