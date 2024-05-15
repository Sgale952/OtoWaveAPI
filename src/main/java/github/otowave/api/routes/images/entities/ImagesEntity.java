package github.otowave.api.routes.images.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "images", schema = "images")
public class ImagesEntity {
    @Id
    private int imageID;
    private int uploaderID;
    private boolean animated;

    public ImagesEntity() {

    }

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getUploaderID() {
        return uploaderID;
    }
    public void setUploaderID(int uploaderID) {
        this.uploaderID = uploaderID;
    }

    public boolean isAnimated() {
        return animated;
    }
    public void setAnimated(boolean animated) {
        this.animated = animated;
    }
}
