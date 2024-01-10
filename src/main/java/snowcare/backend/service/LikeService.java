package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.*;
import snowcare.backend.dto.response.CommunityArticleResponse;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.repository.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeService {
    private final LikeVolunteerRepository likeVolunteerRepository;
    private final LikeCommunityArticleRepository likeCommunityArticleRepository;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final CommunityArticleRepository communityArticleRepository;

    // 봉사활동글 좋아요 추가
    public void addLikeVolunteer(Long userId, Long volunteerId) throws IOException {
        User user = getUserOrThrow(userId);
        Volunteer volunteer = getVolunteerOrThrow(volunteerId);
        LikeVolunteer checkLike = likeVolunteerRepository.findByUserIdAndVolunteerId(userId, volunteerId);
        if (checkLike != null){
            throw new CustomException(ErrorCode.EXIST_LIKE_VOLUNTEER);
        }
        LikeVolunteer likeVolunteer = LikeVolunteer.builder()
                .user(user)
                .volunteer(volunteer)
                .build();
        likeVolunteerRepository.save(likeVolunteer);
        volunteer.addLikeCount();
    }

    // 봉사활동글 좋아요 삭제
    public void deleteLikeVolunteer(Long userId, Long volunteerId) {
        LikeVolunteer likeVolunteer = likeVolunteerRepository.findByUserIdAndVolunteerId(userId, volunteerId);
        Volunteer volunteer = getVolunteerOrThrow(volunteerId);
        if (likeVolunteer == null){
            throw new CustomException(ErrorCode.NOT_FOUND_LIKE_VOLUNTEER);
        }
        likeVolunteerRepository.delete(likeVolunteer);
        volunteer.deleteLikeCount();
    }


    // 사용자의 봉사활동글(Volunteer) 좋아요 목록 조회
    public List<VolunteerResponse> getAllUserLikedVolunteers(Long userId) {
        getUserOrThrow(userId);
        List<Volunteer> volunteers = likeVolunteerRepository.findByUserId(userId).stream()
                .map(like -> like.getVolunteer())
                .collect(Collectors.toList());
        return volunteers.stream()
                .map(v -> VolunteerResponse.builder()
                        .volunteerId(v.getId())
                        .userNickname(v.getUser().getNickname())
                        .userImage(v.getUser().getProfileImage())
                        .title(v.getTitle())
                        .content(v.getContent())
                        .image(v.getImage())
                        .place(v.getPlace())
                        .createdDate(v.getCreatedDate().toLocalDate())
                        .build())
                .collect(Collectors.toList());
    }

    // 커뮤니티글 좋아요 추가
    public void addLikeCommunityArticle(Long userId, Long communityArticleId) throws IOException {
        User user = getUserOrThrow(userId);
        CommunityArticle communityArticle = getCommunityArticleOrThrow(communityArticleId);
        LikeCommunityArticle checkLike = likeCommunityArticleRepository.findByUserIdAndCommunityArticleId(userId, communityArticleId);
        if (checkLike != null){
            throw new CustomException(ErrorCode.EXIST_LIKE_COMMUNITY_ARTICLE);
        }
        LikeCommunityArticle likeCommunityArticle = LikeCommunityArticle.builder()
                .user(user)
                .communityArticle(communityArticle)
                .build();
        likeCommunityArticleRepository.save(likeCommunityArticle);
        communityArticle.addLikeCount();
    }


    // 커뮤니티글 좋아요 삭제
    public void deleteLikeCommunityArticle(Long userId, Long communityArticleId) {
        LikeCommunityArticle likeCommunityArticle = likeCommunityArticleRepository.findByUserIdAndCommunityArticleId(userId, communityArticleId);
        CommunityArticle communityArticle = getCommunityArticleOrThrow(communityArticleId);
        if (likeCommunityArticle == null){
            throw new CustomException(ErrorCode.NOT_FOUND_LIKE_COMMUNITY_ARTICLE);
        }
        likeCommunityArticleRepository.delete(likeCommunityArticle);
        communityArticle.deleteLikeCount();
    }


    // 사용자의 커뮤니티글 좋아요 목록 조회
    public List<CommunityArticleResponse> getAllUserLikedCommunityArticles(Long userId) {
        getUserOrThrow(userId);
        List<CommunityArticle> communityArticles = likeCommunityArticleRepository.findByUserId(userId).stream()
                .map(like -> like.getCommunityArticle())
                .collect(Collectors.toList());
        return communityArticles.stream()
                .map(c -> CommunityArticleResponse.builder()
                        .communityArticleId(c.getId())
                        .userNickname(c.getUser().getNickname())
                        .userImage(c.getUser().getProfileImage())
                        .createdDate(c.getCreatedDate().toLocalDate())
                        .title(c.getTitle())
                        .content(c.getContent())
                        .image(c.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    // 유저가 봉사활동글에 좋아요를 눌렀는가
    public Boolean checkIfUserLikedVolunteer(Long userId, Long volunteerId) {
        LikeVolunteer liked = likeVolunteerRepository.findByUserIdAndVolunteerId(userId, volunteerId);
        if (liked == null) {
            return false;
        }
        return true;
    }

    // 유저가 커뮤니티글에 좋아요를 눌렀는가
    public Boolean checkIfUserLikedCommunityArticle(Long userId, Long communityArticleId) {
        LikeCommunityArticle liked = likeCommunityArticleRepository.findByUserIdAndCommunityArticleId(userId, communityArticleId);
        if (liked == null){
            return false;
        }
        return true;
    }

    // 예외 처리 - 존재하는 User 인가
    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    // 예외 처리 - 존재하는 Volunteer 인가
    private Volunteer getVolunteerOrThrow(Long volunteerId) {
        return volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOLUNTEER));
    }

    // 예외 처리 - 존재하는 CommunityArticle 인가
    private CommunityArticle getCommunityArticleOrThrow(Long communityArticleId) {
        return communityArticleRepository.findById(communityArticleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMUNITY_ARTICLE));
    }
}
