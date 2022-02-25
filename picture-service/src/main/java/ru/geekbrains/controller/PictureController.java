package ru.geekbrains.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.service.PictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{pictureId}")
    public void downloadPicture(@PathVariable("pictureId") long pictureId,
                                HttpServletResponse response) throws IOException {
        Optional<String> opt = pictureService.getPictureContentType(pictureId);
        if (opt.isPresent()) {
            response.setContentType(opt.get());
            response.getOutputStream().write(pictureService.getPictureDataById(pictureId).get());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
