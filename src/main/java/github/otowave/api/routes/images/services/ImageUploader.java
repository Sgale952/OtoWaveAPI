package github.otowave.api.routes.images.services;

public class ImageUploader {
    private final int itemID;
    private final int userID;

    public ImageUploader(int itemID, int userID) {
        this.itemID = itemID;
        this.userID = userID;
    }

/*    public void saveAndApplyImage() {
        int imageID = saveEntity();
    }
    private int saveEntity() {
        YourEntity savedEntity = entityRepository.save(entity);
        return savedEntity.getId();
    }*/
}
