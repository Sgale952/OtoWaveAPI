package github.otowave.api.controlers.music;

import github.otowave.api.entities.music.GenresEntity;
import github.otowave.api.models.music.*;
import github.otowave.api.repositories.music.*;
import github.otowave.api.services.music.faces.TopRecentFacesMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

/*    @GetMapping("/{musicID}/face")
    private MusicFaceModel face() {
        return new MusicFaceModel();
    }*/

    @GetMapping("/genres")
    private Iterable<GenresEntity> genres() {
        return genresRepo.findAll();
    }

    @GetMapping("/top")
    private ArrayList<MusicFaceModel> top(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getTopMusicFaceModels(page, genre);
    }

    @GetMapping("/recent")
    private ArrayList<MusicFaceModel> recent(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topRecentFacesMaker.getRecentMusicFaceModels(page, genre);
    }
}
