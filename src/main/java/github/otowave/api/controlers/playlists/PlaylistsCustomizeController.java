package github.otowave.api.controlers.playlists;

import github.otowave.api.controlers.Customizable;
import github.otowave.api.entities.playlists.PlaylistsEntity;
import github.otowave.api.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.repositories.playlists.PlaylistsMetaRepo;
import github.otowave.api.repositories.playlists.PlaylistsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists/{sourceID}")
public class PlaylistsCustomizeController implements Customizable {
    @Autowired
    private PlaylistsRepo playlistsRepo;
    @Autowired
    private PlaylistsMetaRepo playlistsMetaRepo;
    
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
        PlaylistsEntity playlistEntity = getEntity(sourceID, playlistsRepo);
        playlistEntity.setTitle(newName);
        playlistsRepo.save(playlistEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int sourceID, @RequestParam String newTale) {
        PlaylistsMetaEntity playlistMetaEntity = getEntity(sourceID, playlistsMetaRepo);
        playlistMetaEntity.setTale(newTale);
        playlistsMetaRepo.save(playlistMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int sourceID) {

    }
}
