package github.otowave.api.controlers.music;

import github.otowave.api.controlers.Customizable;
import github.otowave.api.entities.music.MusicEntity;
import github.otowave.api.entities.music.MusicMetaEntity;
import github.otowave.api.repositories.music.MusicMetaRepo;
import github.otowave.api.repositories.music.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music/{musicID}")
public class MusicCustomizeController implements Customizable {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;

    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int musicID) {

    }

    @Override
    @PostMapping("/change-image")
    public void changeImage(@PathVariable int musicID, @RequestParam int prevImageID) {

    }

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int musicID, String newName) {
        MusicEntity musicEntity = getEntity(musicID, musicRepo);
        musicEntity.setTitle(newName);
        musicRepo.save(musicEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int musicID, @RequestParam String newTale) {
        MusicMetaEntity musicMetaEntity = getEntity(musicID, musicMetaRepo);
        musicMetaEntity.setTale(newTale);
        musicMetaRepo.save(musicMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int musicID) {

    }
}
