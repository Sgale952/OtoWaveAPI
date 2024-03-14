package github.otowave.api;

import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylists.PlaylistApi;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        get("/daily", MusicApi::dailyRandom);
        get("/search", (MusicApi::search));

        path("/navigator", () -> {
            get("/recent", (MusicApi::topPerMonth));
            get("/top", (MusicApi::topPerMonth));
        });

        path("/:musicId", () -> {
            //Try merging methods (updateInteraction)
            patch("/update-likes", (MusicApi::updateLikes));
            patch("/update-listens", (MusicApi::updateListens));
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

            post("/new-song", (MusicApi::upload));
            post("/like-song", (MusicApi::like));
            delete("/discard-song", (MusicApi::discard));

            post("/new-playlist", ((request, response) -> PlaylistApi.add(request)));
            post("/add-playlist", ((request, response) -> PlaylistApi.addSong(request)));
            post("/like-playlist", ((request, response) -> PlaylistApi.like(request)));

            path("/:musicId", () -> {
                patch("/update-song", (MusicApi::update));
                delete("/delete-song", (MusicApi::delete));
            });

            path("/:playlistId", () -> {
                patch("/update-playlist", ((request, response) -> PlaylistApi.update(request)));
                delete("/delete-playlist", ((request, response) -> PlaylistApi.delete(request)));
            });
        });
    }
}