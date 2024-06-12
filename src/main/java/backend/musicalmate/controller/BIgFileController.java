package backend.musicalmate.controller;

import backend.musicalmate.service.OauthService;
import backend.musicalmate.service.BigFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
public class BIgFileController {
    private final OauthService oauthService;
    private final BigFileService bigFileService;

    @PostMapping("/api/upload/big/{type}")
    ResponseEntity<CompletableFuture<String>> uploadBigFile(
            @PathVariable String type,
            @RequestPart("smallFiles") List<MultipartFile> smallFiles,
            @RequestPart("fileMeta") String fileMeta,
            @RequestHeader("userId") Long userId
    ) throws IOException {

        //bigFile -> presigned url 주기

        //smallFile -> 백에서 저장

        //fileMeta -> 백에서 저장

        return ResponseEntity.ok(CompletableFuture.completedFuture("a"));
    }
}
