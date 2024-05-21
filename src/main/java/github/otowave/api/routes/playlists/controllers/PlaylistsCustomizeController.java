package github.otowave.api.routes.playlists.controllers;

import github.otowave.api.routes.common.services.items.Customizable;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.ItemTypes.PLAYLIST;

@RestController
@RequestMapping("/playlists/{itemID}")
public class PlaylistsCustomizeController {
    @GetMapping("/profile")
    public Mono<Void> profile(@PathVariable int itemID) {
        return new ItemFactoryImp().makeOpenItem(PLAYLIST, itemID)
                .flatMap(Customizable::profile);
    }

    @PatchMapping("/change-name")
    public Mono<Void> changeName(@PathVariable int itemID, @RequestParam String newName,
                                 @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(PLAYLIST, itemID, authToken)
                .flatMap(item -> item.changeName(newName));
    }

    @PatchMapping("/change-tale")
    public Mono<Void> changeTale(@PathVariable int itemID, @RequestParam String newTale,
                                 @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(PLAYLIST, itemID, authToken)
                .flatMap(item -> item.changeTale(newTale));
    }

    @DeleteMapping("/delete")
    public Mono<Void> delete(@PathVariable int itemID,
                             @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(PLAYLIST, itemID, authToken)
                .flatMap(Customizable::delete);
    }
}
