package github.otowave.api.routes.music.services.genres;

import github.otowave.api.routes.music.entities.GenresEntity;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.models.genres.DailyGenresModel;
import github.otowave.api.routes.music.models.genres.GenreModel;
import github.otowave.api.routes.music.repositories.GenresRepo;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DailyGenresMaker {
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    GenresRepo genresRepo;
    @Autowired
    MusicProfileRepo musicProfileRepo;

    public Mono<DailyGenresModel> getDailyGenreModel() {
        return getGenreModels()
                .collectList()
                .map(DailyGenresModel::new);
    }


    private Flux<GenreModel> getGenreModels() {
        return getGenres()
                .flatMap(genre -> getMusicByGenre(genre.getGenreID())
                        .flatMap(this::getFaceModel)
                        .collectList()
                        .map(musicFaces -> new GenreModel(genre.getGenreID(), musicFaces))
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
