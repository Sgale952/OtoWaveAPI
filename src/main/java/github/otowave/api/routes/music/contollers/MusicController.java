package github.otowave.api.routes.music.contollers;

import github.otowave.api.routes.music.entities.GenresEntity;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.models.*;
import github.otowave.api.routes.music.models.genres.DailyGenresModel;
import github.otowave.api.routes.music.repositories.GenresRepo;
import github.otowave.api.routes.music.services.faces.MusicDailyFaceMaker;
import github.otowave.api.routes.music.services.faces.TopRecentFaceMaker;
import github.otowave.api.routes.music.services.genres.DailyGenresMaker;
import github.otowave.api.routes.music.services.upload.MusicUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.MUSIC;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private GenresRepo genresRepo;
    @Autowired
    private MusicDailyFaceMaker musicDailyFaceMaker;
    @Autowired
    private DailyGenresMaker dailyGenresMaker;
    @Autowired
    private TopRecentFaceMaker topRecentFacesMaker;
    @Autowired
    private MusicUploader musicUploader;

    @GetMapping("/genres")
    private Flux<GenresEntity> genres() {
        return genresRepo.findAll();
    }

    @GetMapping("/daily")
    private Flux<MusicFaceModel> daily() {
        return musicDailyFaceMaker.getDailyFaceModel();
    }

    @GetMapping("/daily-genres")
    private Mono<DailyGenresModel> dailyGenres() {
        return dailyGenresMaker.getDailyGenreModel();
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

    @PostMapping("/upload")
    private Mono<Integer> upload(@RequestParam int authorID, @RequestParam String title, @RequestParam String genre, @RequestParam boolean econtent,
                                 @RequestParam(required = false) String tale, @RequestPart Mono<FilePart> musicFile) {
        MusicProfileEntity profileEntity = new MusicProfileEntity(MUSIC, authorID, title, genre, econtent);
        return musicUploader.upload(profileEntity, tale, musicFile);
    }
}
