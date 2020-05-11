package uk.co.edbrook.oauth2.web.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AppController {

    @GetMapping("/apps")
    public String listApps(Authentication authentication) {
        log.info("method=listApps");

        return "Hello " + authentication.getName() + "!\nGrants: " + authentication.getAuthorities();
    }
}
