package github.otowave.api.routes.users.models;

import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;

import java.time.LocalDateTime;

public class UserProfileModel extends ProfileModel {
    private final int headerID;
    private final int subscribed;
    private final MusicFaceModel[] createdMusic;
    private final MusicFaceModel[] likedMusic;
    private final SonglistFaceModel[] createdSonglists;
    private final SonglistFaceModel[] likedSonglists;

    public UserProfileModel(FaceModel userFace, String tale, int subscribers, LocalDateTime created, int headerID, int subscribed,
                            MusicFaceModel[] createdMusic, MusicFaceModel[] likedMusic, SonglistFaceModel[] createdSonglists, SonglistFaceModel[] likedSonglists) {
        super(userFace, tale, subscribers, created);
        this.headerID = headerID;
        this.subscribed = subscribed;
        this.createdMusic = createdMusic;
        this.likedMusic = likedMusic;
        this.createdSonglists = createdSonglists;
        this.likedSonglists = likedSonglists;
    }

    public int getHeaderID() {
        return headerID;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public MusicFaceModel[] getCreatedMusic() {
        return createdMusic;
    }

    public MusicFaceModel[] getLikedMusic() {
        return likedMusic;
    }

    public SonglistFaceModel[] getCreatedSonglists() {
        return createdSonglists;
    }

    public SonglistFaceModel[] getLikedSonglists() {
        return likedSonglists;
    }
}
