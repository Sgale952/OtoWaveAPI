package github.otowave.api.controlers.music;

import github.otowave.api.controlers.Customizable;
import github.otowave.api.entities.music.MusicEntity;
import github.otowave.api.entities.music.MusicMetaEntity;
import github.otowave.api.repositories.music.MusicMetaRepo;
import github.otowave.api.repositories.music.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music/{sourceID}")
public class MusicCustomizeController implements Customizable {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;

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
        MusicEntity musicEntity = getEntity(sourceID, musicRepo);
        musicEntity.setTitle(newName);
        musicRepo.save(musicEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int sourceID, @RequestParam String newTale) {
        MusicMetaEntity musicMetaEntity = getEntity(sourceID, musicMetaRepo);
        musicMetaEntity.setTale(newTale);
        musicMetaRepo.save(musicMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int sourceID) {

    }
}
