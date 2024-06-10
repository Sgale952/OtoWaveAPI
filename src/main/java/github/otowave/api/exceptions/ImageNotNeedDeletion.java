package github.otowave.api.exceptions;

public class ImageNotNeedDeletion extends RuntimeException {
    public ImageNotNeedDeletion(int imageID) {
        super("imageID: "+imageID);
    }
}
