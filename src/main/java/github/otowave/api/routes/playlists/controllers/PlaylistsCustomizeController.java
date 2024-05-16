package github.otowave.api.routes.playlists.controllers;

import github.otowave.api.routes.common.controllers.Customizable;
import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists/{itemID}")
public class PlaylistsCustomizeController implements Customizable {
    @Autowired
    private PlaylistsRepo playlistsRepo;
    @Autowired
    private PlaylistsMetaRepo playlistsMetaRepo;
    
    @Override
    @GetMapping("/profile")
    public void profile(@PathVariable int itemID) {
        
    }

    @Override
    @PatchMapping("/change-name")
    public void changeName(@PathVariable int itemID, @RequestParam String newName) {
        PlaylistsEntity playlistEntity = getItemEntity(itemID, playlistsRepo);
        playlistEntity.setTitle(newName);
        playlistsRepo.save(playlistEntity);
    }

    @Override
    @PatchMapping("/change-tale")
    public void changeTale(@PathVariable int itemID, @RequestParam String newTale) {
        PlaylistsMetaEntity playlistMetaEntity = getItemEntity(itemID, playlistsMetaRepo);
        playlistMetaEntity.setTale(newTale);
        playlistsMetaRepo.save(playlistMetaEntity);
    }

    @Override
    @DeleteMapping("/delete")
    public void delete(@PathVariable int itemID) {

    }
}
