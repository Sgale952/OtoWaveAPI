package github.otowave.api.routes.common.controllers;

import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.common.services.items.factory.Customizable;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.items.ItemTypes.toItemType;

@RestController
@RequestMapping("/{itemType}/{itemID}")
public class ItemCustomizeController {
    private final ItemFactoryImp itemFactory;

    @Autowired
    public ItemCustomizeController(ItemFactoryImp itemFactory) {
        this.itemFactory = itemFactory;
    }

    @GetMapping("/profile")
    Mono<ProfileModel> profile(@PathVariable String itemType, @PathVariable int itemID) {
        return itemFactory.makeItem(toItemType(itemType), itemID)
                .flatMap(Customizable::profile);
    }

    @PatchMapping("/change-name")
    Mono<Void> changeName(@PathVariable String itemType, @PathVariable int itemID,
                                 @RequestParam String newName) {
        return itemFactory.makeItem(toItemType(itemType), itemID)
                .flatMap(item -> item.changeName(newName));
    }

    @PatchMapping("/change-tale")
    Mono<Void> changeTale(@PathVariable String itemType, @PathVariable int itemID,
                                 @RequestParam String newTale) {
        return itemFactory.makeItem(toItemType(itemType), itemID)
                .flatMap(item -> item.changeTale(newTale));
    }

    @DeleteMapping("/delete")
    Mono<Void> delete(@PathVariable String itemType, @PathVariable int itemID) {
        return itemFactory.makeItem(toItemType(itemType), itemID)
                .flatMap(Customizable::delete);
    }
}