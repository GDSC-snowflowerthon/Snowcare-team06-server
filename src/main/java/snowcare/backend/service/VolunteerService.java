package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.CommentVolunteer;
import snowcare.backend.domain.User;
import snowcare.backend.domain.Volunteer;
import snowcare.backend.dto.request.VolunteerSaveRequest;
import snowcare.backend.dto.response.CommentResponse;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.repository.CommentVolunteerRepository;
import snowcare.backend.repository.UserRepository;
import snowcare.backend.repository.VolunteerRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final ImageService imageService;
    private final LikeService likeService;
    private final UserRepository userRepository;
    private final CommentVolunteerRepository commentVolunteerRepository;

    // 봉사활동 구인글 전체 조회
    public List<VolunteerResponse> getAllVolunteers(Long userId) {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers.stream()
                .map(m -> VolunteerResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .userImage(m.getUser().getProfileImage())
                        .volunteerId(m.getId())
                        .createdDate(m.getCreatedDate().toLocalDate())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(imageService.processImage(m.getImage()))
                        .place(m.getPlace())
                        .likeCount(m.getLikeCount())
                        .userLiked(likeService.checkIfUserLikedVolunteer(userId, m.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    // 봉사활동 구인글 상세 조회
    public VolunteerResponse getVolunteerById(Long volunteerId, Long userId) {
        Volunteer volunteer = getVolunteerOrThrow(volunteerId);
        List<CommentVolunteer> commentVolunteers = commentVolunteerRepository.findByUserIdAndVolunteerId(userId, volunteerId);
        List<CommentResponse> commentResponses = commentVolunteers.stream()
                .map(m -> CommentResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .content(m.getContent())
                        .createdDate(m.getCreatedDate().toLocalDate())
                        .build())
                .collect(Collectors.toList());
        return VolunteerResponse.builder()
                .userNickname(volunteer.getUser().getNickname())
                .userImage(volunteer.getUser().getProfileImage())
                .volunteerId(volunteer.getId())
                .createdDate(volunteer.getCreatedDate().toLocalDate())
                .title(volunteer.getTitle())
                .content(volunteer.getContent())
                .image(imageService.processImage(volunteer.getImage()))
                .place(volunteer.getPlace())
                .likeCount(volunteer.getLikeCount())
                .userLiked(likeService.checkIfUserLikedVolunteer(userId, volunteer.getId()))
                .comments(commentResponses)
                .build();
    }

    // 봉사활동 구인글 최신 3개 조회
    public List<VolunteerResponse> getThreeVolunteers(Long userId) {
        List<Volunteer> volunteers = volunteerRepository.findFirst3ByOrderByIdDesc();
        List<VolunteerResponse> latestVolunteers = volunteers.stream()
                .map(m -> VolunteerResponse.builder()
                        .userNickname(m.getUser().getNickname())
                        .userImage(m.getUser().getProfileImage())
                        .volunteerId(m.getId())
                        .createdDate(m.getCreatedDate().toLocalDate())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(imageService.processImage(m.getImage()))
                        .place(m.getPlace())
                        .likeCount(m.getLikeCount())
                        .userLiked(likeService.checkIfUserLikedVolunteer(userId, m.getId()))
                        .build())
                .collect(Collectors.toList());
        return latestVolunteers;
    }

    // 봉사활동 구인글 작성
    public Long addVolunteer(VolunteerSaveRequest request) throws IOException {
        User user = getUserOrThrow(request.getUserId());
        String imageUUID = null;
        if (!request.getImage().isEmpty()) {
            imageUUID = imageService.uploadImage(request.getImage());
        }
        Volunteer volunteer = Volunteer.createVolunteer(request, user, imageUUID);
        volunteerRepository.save(volunteer);
        return volunteer.getId();
    }

    // 봉사활동 구인글 삭제
    public void deleteVolunteer(Long volunteerId) {
        getVolunteerOrThrow(volunteerId);
        volunteerRepository.deleteById(volunteerId);
    }

    // 예외 처리 - 존재하는 User 인가
    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    // 예외 처리 - 존재하는 봉사활동 구인글인지
    private Volunteer getVolunteerOrThrow(Long volunteerId) {
        return volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VOLUNTEER));
    }
}
