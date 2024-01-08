package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import snowcare.backend.common.entity.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVolunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_volunteer_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    private String content;

    // 연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getCommentVolunteers().add(this);
    }

    // 생성 메서드
    public static CommentVolunteer createCommentVolunteer(User user, Volunteer volunteer, String content) {
        CommentVolunteer commentVolunteer = new CommentVolunteer();
        commentVolunteer.setUser(user);
        commentVolunteer.setVolunteer(volunteer);
        commentVolunteer.setContent(content);

        return commentVolunteer;
    }
}
