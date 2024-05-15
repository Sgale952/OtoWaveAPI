package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.images.repositories.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{sourceType}/{sourceID}")
public class ImagesController {
    private final String IMAGES_DIR = "D:\\";
    @Autowired
    private ImagesRepo imagesRepo;

    @PostMapping("/change-image")
    public String replace(@RequestPart byte[] image) {
        return "";
    }
}
