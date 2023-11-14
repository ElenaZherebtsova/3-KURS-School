package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;
    private final int maxFileSize = 300 * 1024;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar (@RequestParam long studentId,
                                                @RequestParam MultipartFile avatar)
        throws IOException {
        if (avatar.getSize() > maxFileSize) {
            return ResponseEntity.badRequest()
                    .body("Изображение слишком большое для загрузки.");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().body("Изображение успешно загружено.");

    }
}
