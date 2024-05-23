package github.otowave.api.routes.images.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "images", schema = "images")
public class ImagesEntity {
    @Id
    private int imageID;
    private boolean animated;

    public ImagesEntity() {
    }

    public ImagesEntity(boolean animated) {
        this.animated = animated;
    }

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public boolean isAnimated() {
        return animated;
    }
    public void setAnimated(boolean animated) {
        this.animated = animated;
    }
}
