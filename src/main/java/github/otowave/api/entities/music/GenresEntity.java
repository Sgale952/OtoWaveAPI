package github.otowave.api.entities.music;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres", schema = "music")
public class GenresEntity {
    @Id
    private String genreID;

    public String getGenreID() {
        return genreID;
    }
    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }
}
