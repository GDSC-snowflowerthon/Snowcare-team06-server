package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.User;
import snowcare.backend.domain.UserVolunteerPost;
import snowcare.backend.dto.request.UserVolunteerPostSaveRequest;
import snowcare.backend.dto.response.UserVolunteerPostResponse;
import snowcare.backend.repository.UserRepository;
import snowcare.backend.repository.UserVolunteerPostRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserVolunteerPostService {
    private final UserVolunteerPostRepository userVolunteerPostRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

    // 나의 봉사활동 기록하기 전체 조회
    public List<UserVolunteerPostResponse> getAllUserVolunteerPosts(Long userId) {
        List<UserVolunteerPost> userVolunteerPosts = userVolunteerPostRepository.findAll();
        return userVolunteerPosts.stream()
                .map(m -> UserVolunteerPostResponse.builder()
                        .title(m.getTitle())
                        .content(m.getContent())
                        .image(imageService.processImage(m.getImage()))
                        .userVolunteerDate(m.getUserVolunteerDate())
                        .build())
                .collect(Collectors.toList());
    }

    // 나의 봉사활동 기록하기 상세 조회
    public UserVolunteerPostResponse getUserVolunteerPostById(Long userVolunteerPostId, Long userId) {
        UserVolunteerPost userVolunteerPost = getUserVolunteerPostOrThrow(userVolunteerPostId);
        return UserVolunteerPostResponse.builder()
                .title(userVolunteerPost.getTitle())
                .content(userVolunteerPost.getContent())
                .image(imageService.processImage(userVolunteerPost.getImage()))
                .userVolunteerDate(userVolunteerPost.getUserVolunteerDate())
                .build();
    }

    // 나의 봉사활동 기록하기 작성
    public Long addUserVolunteerPost(UserVolunteerPostSaveRequest request) throws IOException {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        String imageUUID = null;
        if (!request.getImage().isEmpty()) {
            imageUUID = imageService.uploadImage(request.getImage());
        }
        UserVolunteerPost userVolunteerPost = UserVolunteerPost.createUserVolunteerPost(request, user, imageUUID);
        userVolunteerPostRepository.save(userVolunteerPost);
        return userVolunteerPost.getId();
    }

    // 예외 처리 - 존재하는 나의 봉사활동 기록인지
    private UserVolunteerPost getUserVolunteerPostOrThrow(Long userVolunteerPostId) {
        return userVolunteerPostRepository.findById(userVolunteerPostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_VOLUNTEER_POST));
    }

}
