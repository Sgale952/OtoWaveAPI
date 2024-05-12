package github.otowave.api.controlers.music;

import github.otowave.api.controlers.Customizable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class MusicMetaController implements Customizable {
    @Override
    public void upload() {

    }

    @Override
    public void delete() {

    }

    @Override
    @PostMapping("/{sourceID}/change-image")
    public void changeImage(@PathVariable int sourceID, @RequestParam int prevImageID) {

    }

    @Override
    @PatchMapping("/{sourceID}/change-name")
    public void changeName() {

    }

    @Override
    @PatchMapping("/{sourceID}/change-tale")
    public void changeTale() {

    }
}
