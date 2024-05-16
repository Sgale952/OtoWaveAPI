package github.otowave.api.routes.music.contollers;

import github.otowave.api.routes.common.controllers.Customizable;
import github.otowave.api.routes.music.entities.MusicEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static github.otowave.api.security.Verifier.verify;

@RestController
@RequestMapping("/music/{itemID}")
public class MusicCustomizeController implements Customizable {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;

    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int itemID) {

    }

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int itemID, @RequestParam String newName) {
        MusicEntity musicEntity = getItemEntity(itemID, musicRepo);
        musicEntity.setTitle(newName);
        musicRepo.save(musicEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int itemID, @RequestParam String newTale) {
        MusicMetaEntity musicMetaEntity = getItemEntity(itemID, musicMetaRepo);
        musicMetaEntity.setTale(newTale);
        musicMetaRepo.save(musicMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int itemID) {

    }
}
