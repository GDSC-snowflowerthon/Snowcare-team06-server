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
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    // 연관관계 메서드
    public void setVolunteer(Volunteer volunteer){
        this.volunteer = volunteer;
        volunteer.getChatMessages().add(this);
    }

    // 생성 메서드
    public static ChatMessage createChatMessage(User user, Volunteer volunteer, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setVolunteer(volunteer);
        chatMessage.setContent(content);

        return chatMessage;
    }
}
