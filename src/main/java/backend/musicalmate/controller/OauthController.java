package backend.musicalmate.controller;

import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.oauth.OauthServerType;
import backend.musicalmate.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OauthController {

    private final OauthService oauthService;
    private static final Logger logger = LoggerFactory.getLogger(OauthController.class);

    @SneakyThrows
    @PostMapping("/api/auth/login/{oauthServerType}")
    ResponseEntity<Map<String, OauthMember>> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestBody Map<String,String> requestBody
            ){

        String accessToken = requestBody.get("accessToken");

        logger.info("accessToken " +accessToken);
        System.out.println("success"+accessToken);

        OauthMember login = oauthService.login(oauthServerType, accessToken);

        Map<String, OauthMember> responseBody = new HashMap<>();
        responseBody.put("users", login);

        //ResponseEntity<OauthMember> ch = ResponseEntity.ok(login);

        return ResponseEntity.ok(responseBody);
    }

}
