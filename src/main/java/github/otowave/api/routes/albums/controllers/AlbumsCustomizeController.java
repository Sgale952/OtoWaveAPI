package github.otowave.api.routes.albums.controllers;

import github.otowave.api.routes.common.controllers.Customizable;
import github.otowave.api.routes.albums.entities.AlbumsEntity;
import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import github.otowave.api.routes.albums.repositories.AlbumsMetaRepo;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums/{itemID}")
public class AlbumsCustomizeController implements Customizable {
    @Autowired
    private AlbumsRepo albumsRepo;
    @Autowired
    private AlbumsMetaRepo albumsMetaRepo;

    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int itemID) {

    }

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int itemID, @RequestParam String newName) {
        AlbumsEntity albumEntity = getItemEntity(itemID, albumsRepo);
        albumEntity.setTitle(newName);
        albumsRepo.save(albumEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int itemID, @RequestParam String newTale) {
        AlbumsMetaEntity albumMetaEntity = getItemEntity(itemID, albumsMetaRepo);
        albumMetaEntity.setTale(newTale);
        albumsMetaRepo.save(albumMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int itemID) {

    }
}
