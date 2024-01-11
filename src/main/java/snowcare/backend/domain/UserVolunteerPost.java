package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import snowcare.backend.common.entity.BaseEntity;
import snowcare.backend.dto.request.UserVolunteerPostSaveRequest;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVolunteerPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_volunteer_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;
    private String content;
    private String image;
    private String userVolunteerDate;

    // 생성 메서드
    public static UserVolunteerPost createUserVolunteerPost(UserVolunteerPostSaveRequest request, User user, String image) {
        UserVolunteerPost userVolunteerPost = UserVolunteerPost.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .image(image)
                .userVolunteerDate(request.getUserVolunteerDate())
                .build();
        return userVolunteerPost;
    }

}
