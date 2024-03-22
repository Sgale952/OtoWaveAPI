package github.otowave.api;

import github.otowave.otoimages.ImagesApi;
import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylists.PlaylistApi;
import github.otowave.otousers.UsersApi;

import static github.otowave.api.CommonUtils.multipartConfig;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        multipartConfig();

        post("/new-user", UsersApi::upload);
        post("/authorization", UsersApi::authorization);
        get("/daily", MusicApi::dailyRandom);
        get("/search", (MusicApi::search));

        path("/navigator", () -> {
            get("/genres", (MusicApi::genres));
            get("/recent", (MusicApi::topPerMonth));
            get("/top", (MusicApi::topPerMonth));
        });

        path("/:musicId", () -> {
            //Try merging methods (updateInteraction)
            patch("/update-likes", (MusicApi::updateLikes));
            patch("/update-listens", (MusicApi::updateListens));
            get("/song-data", (MusicApi::allData));
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
            post("/new-image", (ImagesApi::upload));
            post("/new-song", (MusicApi::upload));
            post("/like-song", (MusicApi::like));
            delete("/discard-song", (MusicApi::discard));

            post("/new-playlist", (PlaylistApi::add));
            post("/add-playlist", (PlaylistApi::addSong));
            post("/like-playlist", (PlaylistApi::like));

            path("/:musicId", () -> {
                patch("/update-song", (MusicApi::update));
                delete("/delete-song", (MusicApi::delete));
            });

            path("/:playlistId", () -> {
                patch("/update-playlist", (PlaylistApi::update));
                delete("/delete-playlist", (PlaylistApi::delete));
            });
        });
    }
}