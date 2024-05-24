package github.otowave.api.routes.users.controlers;

import github.otowave.api.routes.common.services.items.Customizable;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.items.ItemTypes.USER;


@RestController
@RequestMapping("/users/{itemID}")
public class UserCustomizeController {
    private final ItemFactoryImp itemFactory;
    @Autowired
    public UserCustomizeController(ItemFactoryImp itemFactory) {
        this.itemFactory = itemFactory;
    }

    @GetMapping("/profile")
    public Mono<Void> profile(@PathVariable int itemID) {
        return itemFactory.makeItem(USER, itemID)
                .flatMap(Customizable::profile);
    }

    @PatchMapping("/change-name")
    public Mono<Void> changeName(@PathVariable int itemID, @RequestParam String newName,
                                 @CookieValue String authToken) {
        return itemFactory.makeItem(USER, itemID)
                .flatMap(item -> item.changeName(newName));
    }

    @PatchMapping("/change-tale")
    public Mono<Void> changeTale(@PathVariable int itemID, @RequestParam String newTale,
                                 @CookieValue String authToken) {
        return itemFactory.makeItem(USER, itemID)
                .flatMap(item -> item.changeTale(newTale));
    }

    @DeleteMapping("/delete")
    public Mono<Void> delete(@PathVariable int itemID,
                             @CookieValue String authToken) {
        return itemFactory.makeItem(USER, itemID)
                .flatMap(Customizable::delete);
    }
}
