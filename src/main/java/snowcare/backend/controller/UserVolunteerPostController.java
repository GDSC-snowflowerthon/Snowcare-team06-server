package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import snowcare.backend.service.UserVolunteerPostService;

@Service
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/record")
public class UserVolunteerPostController {
    private final UserVolunteerPostService userVolunteerPostService;

    //
}
