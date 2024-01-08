package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users")
public class UserController {
    private final UserService userService;
}
