package uk.co.edbrook.oauth2.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.edbrook.oauth2.domain.KeyPairService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class KeyPairController {

    private final KeyPairService keyPairService;

    @GetMapping(value = "/pub", produces = "application/jwk-set+json")
    public Map<String, List<JSONObject>> getPublicKey() {
        return Map.of("keys",
                List.of(keyPairService.getRsaPublicKey().toJSONObject()));
    }
}
