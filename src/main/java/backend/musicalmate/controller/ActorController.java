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
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ActorController {

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);
    private final ActorService actorService;

    @PostMapping("/api/search/actor")
    public void getActor(
            @RequestParam("name") String actor,
            HttpServletResponse response
    )throws IOException {
        ActorMember actorMember = actorService.findActor(actor);

        //멀티파트 경계 설정
        String boundary = "ActorBoundary";

        //응답 헤더 구성
        response.setContentType("multipart/mixed; boundary="+boundary+"; charset=UTF-8");

        OutputStream out = response.getOutputStream(); // `getOutputStream()`을 사용
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));


        //첫번째 파트 = ActorMember 데이터
        writer.println("--"+boundary);
        writer.println("Content-Type: application/json; charset=UTF-8");
        writer.println();
        writer.println(actorMember);
        writer.flush();

        //두번쨰 파트 = 이미지
        writer.println("--"+boundary);
        writer.println("Content-Type: image/jpeg; charset=UTF-8");
        writer.println("filename="+actor+".jpeg");
        writer.println();

        Path imagePath = Paths.get("C:/computer_capston/musicalmate/src/main/resources/static/ActorImage/"+actor+".jpeg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        out.write(imageBytes);
        out.flush();

        writer.println("--"+boundary+"--");
        writer.flush();

        writer.close();

        //return ResponseEntity.ok(actorMember);
    }
}
