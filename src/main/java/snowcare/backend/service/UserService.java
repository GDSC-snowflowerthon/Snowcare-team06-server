package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import snowcare.backend.common.exception.CustomException;
import snowcare.backend.common.exception.ErrorCode;
import snowcare.backend.domain.User;
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

    // 회원 프로필 이미지 변경
    public void changeProfileImage(Long userId, MultipartFile image) throws IOException {
        User findUser = getUserOrThrow(userId);
        String uuid = null;
        if(image.isEmpty()){
            throw new CustomException(ErrorCode.SHOULD_EXIST_IMAGE);
        }
        uuid = imageService.uploadImage(image);
        findUser.updateProfileImage(uuid);
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
