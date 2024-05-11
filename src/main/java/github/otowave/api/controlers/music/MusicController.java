package github.otowave.api.controlers.music;

import github.otowave.api.entities.music.GenresEntity;
import github.otowave.api.models.music.*;
import github.otowave.api.repositories.music.*;
import github.otowave.api.services.music.faces.TopFreshFacesMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final TopFreshFacesMaker topFreshFacesMaker;

    public MusicController(TopFreshFacesMaker topFreshFacesMaker) {
        this.topFreshFacesMaker = topFreshFacesMaker;
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
        return topFreshFacesMaker.getTopMusicFaceModels(page, genre);
    }

    @GetMapping("/fresh")
    private ArrayList<MusicFaceModel> fresh(@RequestParam int page, @RequestParam(required = false) String genre) {
        return topFreshFacesMaker.getFreshMusicFaceModels(page, genre);
    }
}
