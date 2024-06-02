package github.otowave.api.routes.songlists.controllers;

import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.services.playlists.faces.PlaylistDailyFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.items.ItemTypes.toItemType;

@RestController
@RequestMapping("/{songlistType}")
public class SonglistController {
    private final ItemFactoryImp itemFactory;
    @Autowired
    PlaylistDailyFaceMaker playlistDailyFaceMaker;

    @Autowired
    public SonglistController(ItemFactoryImp itemFactory) {
        this.itemFactory = itemFactory;
    }

    @PostMapping("/upload")
    private Mono<Integer> upload(@PathVariable String songlistType, @RequestParam int creatorID, @RequestParam String title,
                                 @RequestParam(required = false) String tale, @RequestParam boolean access) {
        return itemFactory.makeSonglistItem(toItemType(songlistType), 0)
                .flatMap(item -> item.upload(creatorID, title, tale, access));
    }

    @PostMapping("/{itemID}/add-music")
    private Mono<Void> addMusic(@PathVariable String songlistType, @PathVariable int itemID, int musicID) {
        return itemFactory.makeSonglistItem(toItemType(songlistType), itemID)
                .flatMap(item -> item.addMusic(musicID));
    }

    @PostMapping("/{itemID}/remove-music")
    private Mono<Void> removeMusic(@PathVariable String songlistType, @PathVariable int itemID, int musicID) {
        return itemFactory.makeSonglistItem(toItemType(songlistType), itemID)
                .flatMap(item -> item.removeMusic(musicID));
    }

    @GetMapping("/daily")
    private Flux<SonglistFaceModel> daily() {
        return playlistDailyFaceMaker.getDailyFaceModel();
    }
}
