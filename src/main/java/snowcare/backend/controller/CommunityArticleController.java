package snowcare.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snowcare.backend.service.CommunityArticleService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("community")
public class CommunityArticleController {
    private final CommunityArticleService communityArticleService;
}
