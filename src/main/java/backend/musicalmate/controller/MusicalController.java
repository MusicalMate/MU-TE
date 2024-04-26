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

@RestController
@RequiredArgsConstructor
public class MusicalController {

    Logger logger = LoggerFactory.getLogger(MusicalController.class);
    private final MusicalService musicalService;

    @PostMapping("/api/search/musical")
    ResponseEntity<MusicalMember> getMusical(
            @RequestParam("title") String title
            ){
        MusicalMember musicalMember = musicalService.findMusical(title);

        return ResponseEntity.ok(musicalMember);
    }
}
