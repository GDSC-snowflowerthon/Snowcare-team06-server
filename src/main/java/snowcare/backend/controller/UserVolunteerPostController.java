package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.service.UserVolunteerPostService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/record")
public class UserVolunteerPostController {
    private final UserVolunteerPostService userVolunteerPostService;

    //
}
