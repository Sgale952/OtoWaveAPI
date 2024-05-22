package github.otowave.api.routes.actions.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "listened_music", schema = "actions")
public class ListenedMusicEntity {
    @Id
    private int userID;
    private int musicID;
    private LocalDateTime listened;

    public ListenedMusicEntity() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public LocalDateTime getListened() {
        return listened;
    }
    public void setListened(LocalDateTime listened) {
        this.listened = listened;
    }
}
