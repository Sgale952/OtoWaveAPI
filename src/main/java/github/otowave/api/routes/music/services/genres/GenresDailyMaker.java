package github.otowave.api.routes.music.services.genres;

import github.otowave.api.routes.music.entities.GenresEntity;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.models.GenresModel;
import github.otowave.api.routes.music.repositories.GenresRepo;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenresDailyMaker {
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    GenresRepo genresRepo;
    @Autowired
    MusicProfileRepo musicProfileRepo;

    public Flux<GenresModel> getDailyGenreModel() {
        return getGenres()
                .flatMap(genre -> getMusicByGenre(genre.getGenreID())
                        .flatMap(this::getFaceModel)
                        .collectList()
                        .map(musicFaces -> new GenresModel(genre.getGenreID(), musicFaces))
                );
    }

    private Flux<GenresEntity> getGenres() {
        return genresRepo.findAll();
    }

    private Flux<MusicProfileEntity> getMusicByGenre(String genreID) {
        return musicProfileRepo.findPageByGenre(genreID);
    }

    private Mono<MusicFaceModel> getFaceModel(MusicProfileEntity musicProfile) {
        return musicFaceMaker.getFaceModelsFromProfile(Flux.just(musicProfile)).singleOrEmpty();
    }
}
