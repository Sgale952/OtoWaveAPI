package github.otowave.api.routes.users.controlers;

import github.otowave.api.routes.abstracts.controllers.Customizable;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{itemID}")
public class UserCustomizeController implements Customizable {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int itemID) {

    }

    @Override
    @GetMapping("/reset-image")
    public void resetImage(@PathVariable int itemID) {

    }

/*    @Override
    @PostMapping("/change-image")
    public void changeImage(@PathVariable int itemID, @RequestParam int prevImageID) {

    }*/

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int itemID, @RequestParam String newName) {
        UsersProfileEntity userEntity = getItemEntity(itemID, usersProfileRepo);
        userEntity.setNickname(newName);
        usersProfileRepo.save(userEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int itemID, @RequestParam String newTale) {
        UsersProfileEntity userEntity = getItemEntity(itemID, usersProfileRepo);
        userEntity.setTale(newTale);
        usersProfileRepo.save(userEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int itemID) {

    }
}
