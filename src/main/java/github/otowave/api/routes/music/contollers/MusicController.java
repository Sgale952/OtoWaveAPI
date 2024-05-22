package github.otowave.api.routes.music.contollers;

import github.otowave.api.routes.music.entities.GenresEntity;
import github.otowave.api.routes.music.models.*;
import github.otowave.api.routes.music.repositories.GenresRepo;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicRepo;
import github.otowave.api.routes.music.services.faces.TopRecentFacesMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;
    @Autowired
    private GenresRepo genresRepo;
    private final TopRecentFacesMaker topRecentFacesMaker;

    public MusicController(TopRecentFacesMaker topRecentFacesMaker) {
        this.topRecentFacesMaker = topRecentFacesMaker;
    }

    @GetMapping("/daily")
    private DailyModel daily() {
        return new DailyModel();
    }

    @GetMapping("/subs-new")
    private SubsNewModel subsNew() {
        return new SubsNewModel();
    }

    @GetMapping("/genres")
    private Flux<GenresEntity> genres() {
        return genresRepo.findAll();
    }

    @GetMapping("/top")
    private Flux<MusicFaceModel> top(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getTopMusicFaceModels(page, genre);
    }

    @GetMapping("/recent")
    private Flux<MusicFaceModel> recent(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getRecentMusicFaceModels(page, genre);
    }
}
