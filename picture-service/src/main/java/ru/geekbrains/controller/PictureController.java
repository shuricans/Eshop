package ru.geekbrains.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.service.PictureService;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/picture")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{pictureId}")
    public ResponseEntity<byte[]> downloadPicture(@PathVariable("pictureId") long pictureId) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pictureService.getPictureDataById(pictureId)
                .map(pic -> ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, pic.getContentType())
                        .body(pic.getData())
                ).orElse(ResponseEntity
                        .notFound()
                        .build());
    }
}
