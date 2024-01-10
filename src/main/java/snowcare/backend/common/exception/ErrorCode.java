package snowcare.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User 예외
//    UNAUTHORIZED_ID(HttpStatus.UNAUTHORIZED, "아이디가 틀립니다."),
//    UNAUTHORIZED_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 틀립니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    EXIST_USER_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),

    // Volunteer 예외
    NOT_FOUND_VOLUNTEER(HttpStatus.NOT_FOUND, "해당 봉사활동 글을 찾을 수 없습니다."),

    // CommunityArticle 예외
    NOT_FOUND_COMMUNITY_ARTICLE(HttpStatus.NOT_FOUND, "해당 커뮤니티 글을 찾을 수 없습니다."),

    // like 예외
    NOT_FOUND_LIKE_VOLUNTEER(HttpStatus.NOT_FOUND, "해당 봉사활동글에 좋아요를 하지 않았습니다."),
    NOT_FOUND_LIKE_COMMUNITY_ARTICLE(HttpStatus.NOT_FOUND, "해당 커뮤니티글에 좋아요를 하지 않았습니다."),
    EXIST_LIKE_VOLUNTEER(HttpStatus.CONFLICT, "이미 해당 봉사활동글에 좋아요를 했습니다."),
    EXIST_LIKE_COMMUNITY_ARTICLE(HttpStatus.CONFLICT, "이미 해당 커뮤니티글에 좋아요를 했습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
