package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.User;
import snowcare.backend.dto.request.UserSettingChangeRequest;
import snowcare.backend.dto.response.UserResponse;
import snowcare.backend.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User findUser = getUserOrThrow(userId);
        String imageUrl = imageService.processImage(findUser.getProfileImage());

        UserResponse response = UserResponse.builder()
                .userId(findUser.getId())
                .email(findUser.getEmail())
                .nickname(findUser.getNickname())
                .profileImage(imageUrl)
                .region(findUser.getRegion())
                .weatherAlarm(findUser.getWeatherAlarm())
                .newVolunteerAlarm(findUser.getNewVolunteerAlarm())
                .build();
        return response;
    }

    // 회원 프로필 이미지 변경
    public void changeProfileImage(Long userId, MultipartFile image) throws IOException {
        User findUser = getUserOrThrow(userId);
        String uuid = null;
        if(!image.isEmpty()){
            uuid = imageService.uploadImage(image);
        }
        findUser.updateProfileImage(uuid);
    }

    // 닉네임 중복 조회
    @Transactional(readOnly = true)
    public void checkNicknameDuplicate(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.EXIST_USER_NICKNAME);
        }
    }

    // 닉네임 변경
    public void changeUserNickname(Long userId, String nickname) {
        User findUser = getUserOrThrow(userId);
        checkNicknameDuplicate(nickname);
        findUser.updateNickname(nickname);
    }

    // 설정 변경
    public void changeUserSetting(UserSettingChangeRequest request) {
        User findUser = getUserOrThrow(request.getUserId());
        findUser.updateSetting(request.getRegion(), request.getNewVolunteerAlarm(), request.getWeatherAlarm());
    }

    // 예외 처리 - 존재하는 user인지
    public User getUserOrThrow(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    // 예외 처리 - 이미지 존재여부
    private void checkExistsImage(MultipartFile image) {
        if ((image == null) || image.isEmpty()) {
            throw new CustomException(ErrorCode.SHOULD_EXIST_IMAGE);
        }
    }
}
