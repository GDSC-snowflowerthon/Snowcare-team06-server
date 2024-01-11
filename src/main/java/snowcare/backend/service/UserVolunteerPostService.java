package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.domain.UserVolunteerPost;
import snowcare.backend.dto.response.UserVolunteerPostResponse;
import snowcare.backend.repository.UserVolunteerPostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserVolunteerPostService {
    private final UserVolunteerPostRepository userVolunteerPostRepository;
    private final ImageService imageService;

    // 내 봉사활동 기록하기 전체 조회
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

    /*

     */
}
