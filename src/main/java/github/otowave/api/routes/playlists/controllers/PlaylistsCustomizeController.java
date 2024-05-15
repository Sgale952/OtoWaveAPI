package github.otowave.api.routes.playlists.controllers;

import github.otowave.api.routes.abstracts.controllers.Customizable;
import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
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

/*    @Override
    @PostMapping("/change-image")
    public void changeImage(@PathVariable int sourceID, @RequestParam int prevImageID) {

    }*/

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
