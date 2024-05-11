package github.otowave.api.controlers.images;

import github.otowave.api.repositories.images.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImagesController {
    @Autowired
    private ImagesRepo imagesRepo;
}
