package snowcare.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String kakaoId;
    private String password;
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikeVolunteer> likeVolunteers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikeCommunityArticle> likeCommunityArticles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentVolunteer> commentVolunteers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentCommunityArticle> commentCommunityArticles = new ArrayList<>();


    @Column(nullable = false)
    private String email;
    @Column(unique = true, length = 20)
    private String nickname;
    @Builder.Default
    private String role="ROLE_USER";
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
    }

    private String region;
    @Builder.Default
    private Boolean weatherAlarm=false;
    @Builder.Default
    private Boolean newVolunteerAlarm=false;

    // UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }

    public void addLikeVolunteer(LikeVolunteer likeVolunteer){
        likeVolunteers.add(likeVolunteer);
        likeVolunteer.setUser(this);
    }

    public void addLikeCommunityArticle(LikeCommunityArticle likeCommunityArticle){
        likeCommunityArticles.add(likeCommunityArticle);
        likeCommunityArticle.setUser(this);
    }

    public void addCommentVolunteer(CommentVolunteer commentVolunteer){
        commentVolunteers.add(commentVolunteer);
        commentVolunteer.setUser(this);
    }

    public void addCommentCommunityArticle(CommentCommunityArticle commentCommunityArticle){
        commentCommunityArticles.add(commentCommunityArticle);
        commentCommunityArticle.setUser(this);
    }


    // UserDetails
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {  return String.valueOf(this.id); }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() {return true; }


    public void updateProfileImage(String imageUUID) {
        this.profileImage = imageUUID;
    }
    public void updateNickname(String nickname) {this.nickname = nickname;}
    public void updateSetting(String region, Boolean newVolunteerAlarm, Boolean weatherAlarm){
        this.region = region;
        this.newVolunteerAlarm = newVolunteerAlarm;
        this.weatherAlarm = weatherAlarm;
    }
}
