package github.otowave.api.routes.albums.controllers;

import github.otowave.api.routes.common.services.items.Customizable;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.ItemTypes.ALBUM;

@RestController
@RequestMapping("/albums/{itemID}")
public class AlbumsCustomizeController {
    @GetMapping("/profile")
    public Mono<Void> profile(@PathVariable int itemID) {
        return new ItemFactoryImp().makeOpenItem(ALBUM, itemID)
                .flatMap(Customizable::profile);
    }

    @PatchMapping("/change-name")
    public Mono<Void> changeName(@PathVariable int itemID, @RequestParam String newName,
                                 @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(ALBUM, itemID, authToken)
                .flatMap(item -> item.changeName(newName));
    }

    @PatchMapping("/change-tale")
    public Mono<Void> changeTale(@PathVariable int itemID, @RequestParam String newTale,
                                 @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(ALBUM, itemID, authToken)
                .flatMap(item -> item.changeTale(newTale));
    }

    @DeleteMapping("/delete")
    public Mono<Void> delete(@PathVariable int itemID,
                             @CookieValue String authToken) {
        return new ItemFactoryImp().makeCloseItem(ALBUM, itemID, authToken)
                .flatMap(Customizable::delete);
    }
}
