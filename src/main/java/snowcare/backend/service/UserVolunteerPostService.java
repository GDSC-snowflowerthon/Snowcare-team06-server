package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.repository.UserVolunteerPostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserVolunteerPostService {
    private final UserVolunteerPostRepository userVolunteerPostRepository;
}
