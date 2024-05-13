package github.otowave.api.controlers.albums;

import github.otowave.api.controlers.Customizable;
import github.otowave.api.entities.albums.AlbumsEntity;
import github.otowave.api.entities.albums.AlbumsMetaEntity;
import github.otowave.api.repositories.albums.AlbumsMetaRepo;
import github.otowave.api.repositories.albums.AlbumsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums/{sourceID}")
public class AlbumsCustomizeController implements Customizable {
    @Autowired
    private AlbumsRepo albumsRepo;
    @Autowired
    private AlbumsMetaRepo albumsMetaRepo;

    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int sourceID) {

    }

    @Override
    @PostMapping("/change-image")
    public void changeImage(@PathVariable int sourceID, @RequestParam int prevImageID) {

    }

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int sourceID, @RequestParam String newName) {
        AlbumsEntity albumEntity = getEntity(sourceID, albumsRepo);
        albumEntity.setTitle(newName);
        albumsRepo.save(albumEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int sourceID, @RequestParam String newTale) {
        AlbumsMetaEntity albumMetaEntity = getEntity(sourceID, albumsMetaRepo);
        albumMetaEntity.setTale(newTale);
        albumsMetaRepo.save(albumMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int sourceID) {

    }
}
