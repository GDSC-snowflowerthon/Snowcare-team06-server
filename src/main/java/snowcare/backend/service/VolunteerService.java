package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowcare.backend.repository.VolunteerRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
}
