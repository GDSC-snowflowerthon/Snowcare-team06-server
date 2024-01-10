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
public class LikeVolunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_volunteer_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    // 연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getLikeVolunteers().add(this);
    }

    // 생성 메서드
    public static LikeVolunteer createLikeVolunteer(User user, Volunteer volunteer) {
        LikeVolunteer likeVolunteer = new LikeVolunteer();
        likeVolunteer.setUser(user);
        likeVolunteer.setVolunteer(volunteer);

        return likeVolunteer;
    }
}
