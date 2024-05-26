package github.otowave.api.routes.music.contollers;

import github.otowave.api.routes.music.entities.GenresEntity;
import github.otowave.api.routes.music.models.*;
import github.otowave.api.routes.music.repositories.GenresRepo;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicRepo;
import github.otowave.api.routes.music.services.faces.DailyFaceMaker;
import github.otowave.api.routes.music.services.faces.TopRecentFacesMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private GenresRepo genresRepo;
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;
    @Autowired
    private DailyFaceMaker dailyFaceMaker;
    @Autowired
    private TopRecentFacesMaker topRecentFacesMaker;

    @GetMapping("/genres")
    private Flux<GenresEntity> genres() {
        return genresRepo.findAll();
    }

    @GetMapping("/daily")
    private Flux<MusicFaceModel> daily() {
        return dailyFaceMaker.getDailyMusicFaceModel();
    }

/*    @GetMapping("/subs-new")
    private Flux<MusicFaceModel> subsNew() {
        return new SubsNewModel();
    }*/

    @GetMapping("/top")
    private Flux<MusicFaceModel> top(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getTopMusicFaceModels(page, genre);
    }

    @GetMapping("/recent")
    private Flux<MusicFaceModel> recent(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getRecentMusicFaceModels(page, genre);
    }
}
