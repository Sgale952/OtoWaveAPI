package github.otowave.api.routes.music.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
