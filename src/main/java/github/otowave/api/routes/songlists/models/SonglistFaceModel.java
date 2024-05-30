package github.otowave.api.routes.songlists.models;

import github.otowave.api.routes.common.models.FaceModel;

public class SonglistFaceModel extends FaceModel {
    private final String title;
    private final String genre;
    private final boolean econtent;

    public SonglistFaceModel(int songlistID, int authorID, int coverID, String authorName, String title, String genre, boolean econtent) {
        super(songlistID, authorID, coverID, authorName);
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
