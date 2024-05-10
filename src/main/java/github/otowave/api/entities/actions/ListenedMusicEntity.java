package github.otowave.api.entities.actions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "listenedMusic", schema = "actions")
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
