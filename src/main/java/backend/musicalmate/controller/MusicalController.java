package backend.musicalmate.controller;

import backend.musicalmate.Member.MusicalMember;
import backend.musicalmate.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class MusicalController {

    Logger logger = LoggerFactory.getLogger(MusicalController.class);
    private final MusicalService musicalService;

    @PostMapping("/api/search/musical")
    ResponseEntity<MusicalMember> getMusical(
            @RequestParam("title") String title
            ) throws IOException {
        MusicalMember musicalMember = musicalService.findMusical(title);

        title = title.replaceAll(" ","");
        title = title.toLowerCase(Locale.ROOT);

        Path imagePath = Paths.get("src/main/resources/static/MusicalImage/"+title+".jpeg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

        musicalMember.setImage(encodedImage);

        return ResponseEntity.ok(musicalMember);
    }
}
