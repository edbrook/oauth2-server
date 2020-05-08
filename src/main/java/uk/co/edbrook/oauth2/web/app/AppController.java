package uk.co.edbrook.oauth2.web.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AppController {

    @GetMapping("/apps")
    public String listApps() {
        return "WORKING!";
    }
}
