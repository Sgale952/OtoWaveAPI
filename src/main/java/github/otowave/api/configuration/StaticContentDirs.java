package github.otowave.api.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public enum StaticContentDirs {
    MUSIC_DIR,
    IMAGES_DIR;

    private String dir;

    public String getDir() {
        return dir;
    }

    void setDir(String dir) {
        this.dir = dir;
    }
}

@Component
class FileStorageConfig {
    @Value("${otowave.staticContentStorage.MUSIC_DIR}")
    private String musicDir;

    @Value("${otowave.staticContentStorage.IMAGES_DIR}")
    private String imagesDir;

    @PostConstruct
    public void init() {
        StaticContentDirs.MUSIC_DIR.setDir(musicDir);
        StaticContentDirs.IMAGES_DIR.setDir(imagesDir);
    }
}
