package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import snowcare.backend.common.entity.BaseEntity;
import snowcare.backend.dto.request.VolunteerSaveRequest;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Volunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Column(nullable = false)
    private String title;
    private String content;
    @Column(nullable = false)
    private String place;
    private String image;

    private int likeCount;

    // 생성 메서드
    public static Volunteer createVolunteer(VolunteerSaveRequest request, User user, String image) {
        Volunteer volunteer = Volunteer.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .place(request.getPlace())
                .image(image)
                .likeCount(0)
                .build();
        return volunteer;
    }

    // 좋아요 개수 추가
    public void addLikeCount() {
        this.likeCount++;
    }

    // 좋아요 개수 감소
    public void subLikeCount() {
        this.likeCount--;
    }

    public void addChatMessage(ChatMessage chatMessage){
        chatMessages.add(chatMessage);
        chatMessage.setVolunteer(this);
    }
}
