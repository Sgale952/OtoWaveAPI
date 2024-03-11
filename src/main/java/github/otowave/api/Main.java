package github.otowave.api;

import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylists.PlaylistApi;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.setupConnection();

        get("/daily", (request, response) -> MusicApi.dailyRandom(request));
        get("/search", ((request, response) -> MusicApi.search(request)));
        get("/navigator", ((request, response) -> MusicApi.sortByGenre(request)));

        path("/navigator", () -> {
            get("/recent", ((request, response) -> MusicApi.recentUploaded(request)));
            get("/top", ((request, response) -> MusicApi.mostListensPerMonth(request)));
        });

        path("/:musicId", () -> {
            patch("/update-likes", ((request, response) -> MusicApi.updateLikes(request)));
            patch("/update-listens", ((request, response) -> MusicApi.updateListens(request)));
        });

        path("/:userId", () -> {
            get("/history", ((request, response) -> ""));
            get("/liked", ((request, response) -> ""));
            get("/subscribed", ((request, response) -> ""));
            patch("/ban", ((request, response) -> ""));
            patch("/change-header", ((request, response) -> ""));
            patch("/change-name", ((request, response) -> ""));
            post("/subscribe-user", ((request, response) -> ""));
            delete("/discard-user", ((request, response) -> ""));

            post("/new-song", ((request, response) -> MusicApi.add(request)));
            post("/like-song", ((request, response) -> MusicApi.like(request)));
            delete("/discard-song", ((request, response) -> MusicApi.discard(request)));

            post("/new-playlist", ((request, response) -> PlaylistApi.add(request)));
            post("/add-playlist", ((request, response) -> PlaylistApi.addSong(request)));
            post("/like-playlist", ((request, response) -> PlaylistApi.like(request)));

            path("/:musicId", () -> {
                patch("/update-song", ((request, response) -> MusicApi.update(request)));
                delete("/delete-song", ((request, response) -> MusicApi.delete(request)));
            });

            path("/:playlistId", () -> {
                patch("/update-playlist", ((request, response) -> PlaylistApi.update(request)));
                delete("/delete-playlist", ((request, response) -> PlaylistApi.delete(request)));
            });
        });
    }
}