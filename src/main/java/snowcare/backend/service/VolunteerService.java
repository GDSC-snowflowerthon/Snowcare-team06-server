package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.domain.Volunteer;
import snowcare.backend.dto.response.VolunteerResponse;
import snowcare.backend.repository.VolunteerRepository;

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


}
