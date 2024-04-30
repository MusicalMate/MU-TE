package backend.musicalmate.controller;

import backend.musicalmate.Member.ActorMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.oauth.OauthServerType;
import backend.musicalmate.service.ActorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ActorController {

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);
    private final ActorService actorService;

    @PostMapping("/api/search/actor")
    ResponseEntity<ActorMember> getActor(
            @RequestParam("name") String actor)throws IOException {

        ActorMember actorMember = actorService.findActor(actor);

        Path imagePath = Paths.get("src/main/resources/static/ActorImage/"+actor+".jpeg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

        actorMember.setImage(encodedImage);

        return ResponseEntity.ok(actorMember);
    }
}
