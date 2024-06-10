package github.otowave.api.routes.music.models;

import github.otowave.api.routes.common.models.FaceModel;

public class MusicFaceModel extends FaceModel {
    private final String title;
    private final String genre;
    private final boolean econtent;

    public MusicFaceModel(int musicID, int authorID, int coverID, String authorName, String title, String genre, boolean econtent) {
        super(musicID, authorID, coverID, authorName);
        this.title = title;
        this.genre = genre;
        this.econtent = econtent;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isEcontent() {
        return econtent;
    }
}
